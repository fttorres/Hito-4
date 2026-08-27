package com.neonpulse.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neonpulse.application.service.PurchaseService;
import com.neonpulse.domain.entity.Concert;
import com.neonpulse.domain.entity.ShoppingCart;
import com.neonpulse.domain.exception.OutOfStockException;
import com.neonpulse.domain.repository.ConcertRepository;
import com.neonpulse.domain.valueobject.CardNumber;
import com.neonpulse.domain.valueobject.ConcertId;
import com.neonpulse.domain.valueobject.Money;
import com.neonpulse.infrastructure.web.dto.PurchaseRequest;
import com.neonpulse.infrastructure.web.dto.TicketPurchaseItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PurchaseService purchaseService;

    @MockBean
    private ConcertRepository concertRepository;

    private Concert testConcert;

    @BeforeEach
    void setUp() {
        testConcert = new Concert(
                ConcertId.of("EVT-001"),
                "Rock Festival 2026",
                "2026-10-10",
                "ACTIVE",
                Money.of(BigDecimal.valueOf(100.0)),
                50
        );
    }

    @Test
    @DisplayName("POST /api/v1/purchases retorna 201 Created al procesar compra válida")
    void createPurchase_WhenValid_ShouldReturnCreated() throws Exception {
        given(concertRepository.findById(ConcertId.of("EVT-001"))).willReturn(Optional.of(testConcert));
        doNothing().when(purchaseService).execute(any(ShoppingCart.class), any(CardNumber.class));

        PurchaseRequest request = new PurchaseRequest(
                "4532111122223333",
                List.of(new TicketPurchaseItemRequest("EVT-001", 2, BigDecimal.valueOf(100.0)))
        );

        mockMvc.perform(post("/api/v1/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("SUCCESS")))
                .andExpect(jsonPath("$.totalQuantity", is(2)))
                .andExpect(jsonPath("$.totalAmount", is(200.0)));
    }

    @Test
    @DisplayName("POST /api/v1/purchases retorna 422 Unprocessable Entity ante OutOfStockException")
    void createPurchase_WhenOutOfStock_ShouldReturnUnprocessableEntity() throws Exception {
        given(concertRepository.findById(ConcertId.of("EVT-001"))).willReturn(Optional.of(testConcert));
        doThrow(new OutOfStockException("Stock insuficiente para completar la compra."))
                .when(purchaseService).execute(any(ShoppingCart.class), any(CardNumber.class));

        PurchaseRequest request = new PurchaseRequest(
                "4532111122223333",
                List.of(new TicketPurchaseItemRequest("EVT-001", 10, BigDecimal.valueOf(100.0)))
        );

        mockMvc.perform(post("/api/v1/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status", is(422)))
                .andExpect(jsonPath("$.error", is("BUSINESS_RULE_VIOLATION")))
                .andExpect(jsonPath("$.message", is("Stock insuficiente para completar la compra.")));
    }

    @Test
    @DisplayName("POST /api/v1/purchases retorna 404 Not Found cuando el concerto no existe")
    void createPurchase_WhenConcertNotFound_ShouldReturnNotFound() throws Exception {
        given(concertRepository.findById(ConcertId.of("EVT-999"))).willReturn(Optional.empty());

        PurchaseRequest request = new PurchaseRequest(
                "4532111122223333",
                List.of(new TicketPurchaseItemRequest("EVT-999", 2, BigDecimal.valueOf(100.0)))
        );

        mockMvc.perform(post("/api/v1/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("RESOURCE_NOT_FOUND")));
    }

    @Test
    @DisplayName("GET /api/v1/purchases retorna 200 OK")
    void getAllPurchases_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/purchases"))
                .andExpect(status().isOk());
    }
}

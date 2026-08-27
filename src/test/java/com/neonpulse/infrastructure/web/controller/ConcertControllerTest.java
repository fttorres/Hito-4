package com.neonpulse.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neonpulse.domain.entity.Concert;
import com.neonpulse.domain.repository.ConcertRepository;
import com.neonpulse.domain.valueobject.ConcertId;
import com.neonpulse.domain.valueobject.Money;
import com.neonpulse.infrastructure.web.dto.ConcertRequestDto;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConcertController.class)
class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConcertRepository concertRepository;

    private Concert concert;

    @BeforeEach
    void setUp() {
        concert = new Concert(
                ConcertId.of("EVT-12345678"),
                "Concert Test",
                "2026-10-10",
                "ACTIVE",
                Money.of(new BigDecimal("100.00")),
                50
        );
    }

    @Test
    @DisplayName("GET /api/v1/concerts retorna 200 OK y lista de concertos")
    void getAllConcerts_ShouldReturnOk() throws Exception {
        given(concertRepository.findAll()).willReturn(List.of(concert));

        mockMvc.perform(get("/api/v1/concerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("EVT-12345678")))
                .andExpect(jsonPath("$[0].band", is("Concert Test")))
                .andExpect(jsonPath("$[0].date", is("2026-10-10")))
                .andExpect(jsonPath("$[0].totalTickets", is(50)));
    }

    @Test
    @DisplayName("GET /api/v1/concerts/{id} retorna 200 OK cuando existe")
    void getConcertById_WhenExists_ShouldReturnOk() throws Exception {
        given(concertRepository.findById(ConcertId.of("EVT-12345678"))).willReturn(Optional.of(concert));

        mockMvc.perform(get("/api/v1/concerts/EVT-12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("EVT-12345678")))
                .andExpect(jsonPath("$.band", is("Concert Test")));
    }

    @Test
    @DisplayName("GET /api/v1/concerts/{id} retorna 404 Not Found con ErrorResponse estructurado")
    void getConcertById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        given(concertRepository.findById(ConcertId.of("EVT-999"))).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/concerts/EVT-999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("RESOURCE_NOT_FOUND")))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("POST /api/v1/concerts retorna 201 Created al crear concerto válido")
    void createConcert_WhenValid_ShouldReturnCreated() throws Exception {
        ConcertRequestDto request = new ConcertRequestDto("EVT-NEW", "New Concert", "2026-10-10", "ACTIVE", 100);

        mockMvc.perform(post("/api/v1/concerts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.band", is("New Concert")))
                .andExpect(jsonPath("$.date", is("2026-10-10")))
                .andExpect(jsonPath("$.totalTickets", is(100)))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("POST /api/v1/concerts retorna 400 Bad Request cuando el body es inválido")
    void createConcert_WhenInvalid_ShouldReturnBadRequest() throws Exception {
        ConcertRequestDto invalidRequest = new ConcertRequestDto("", "", "", "", -5);

        mockMvc.perform(post("/api/v1/concerts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("VALIDATION_ERROR")))
                .andExpect(jsonPath("$.details", hasSize(4)));
    }

    @Test
    @DisplayName("PUT /api/v1/concerts/{id} retorna 200 OK al actualizar")
    void updateConcert_WhenExists_ShouldReturnOk() throws Exception {
        given(concertRepository.findById(ConcertId.of("EVT-001"))).willReturn(Optional.of(concert));
        ConcertRequestDto updateRequest = new ConcertRequestDto("EVT-001", "Rock Festival 2026 - Updated", "2026-10-10", "ACTIVE", 40);

        mockMvc.perform(put("/api/v1/concerts/EVT-001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.band", is("Rock Festival 2026 - Updated")))
                .andExpect(jsonPath("$.date", is("2026-10-10")))
                .andExpect(jsonPath("$.availableStock", is(40)));
    }

    @Test
    @DisplayName("DELETE /api/v1/concerts/{id} retorna 204 No Content al eliminar")
    void deleteConcert_WhenExists_ShouldReturnNoContent() throws Exception {
        given(concertRepository.findById(ConcertId.of("EVT-001"))).willReturn(Optional.of(concert));
        doNothing().when(concertRepository).deleteById(ConcertId.of("EVT-001"));

        mockMvc.perform(delete("/api/v1/concerts/EVT-001"))
                .andExpect(status().isNoContent());
    }
}

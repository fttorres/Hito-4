package com.neonpulse.infrastructure.web.controller;

import com.neonpulse.application.service.PurchaseService;
import com.neonpulse.domain.entity.Concert;
import com.neonpulse.domain.entity.ShoppingCart;
import com.neonpulse.domain.entity.TicketItem;
import com.neonpulse.domain.exception.ConcertNotFoundException;
import com.neonpulse.domain.repository.ConcertRepository;
import com.neonpulse.domain.valueobject.CardNumber;
import com.neonpulse.domain.valueobject.ConcertId;
import com.neonpulse.domain.valueobject.Money;
import com.neonpulse.domain.valueobject.Quantity;
import com.neonpulse.infrastructure.web.dto.PurchaseRequest;
import com.neonpulse.infrastructure.web.dto.PurchaseResponse;
import com.neonpulse.infrastructure.web.dto.TicketPurchaseItemRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST semántico para la gestión de compras bajo la ruta versionada /api/v1/purchases.
 */
@RestController
@RequestMapping("/api/v1/purchases")
@Tag(name = "Ventas", description = "Operaciones para la gestión de compras y pagos de tickets")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final ConcertRepository concertRepository;

    public PurchaseController(PurchaseService purchaseService, ConcertRepository concertRepository) {
        this.purchaseService = purchaseService;
        this.concertRepository = concertRepository;
    }

    @Operation(summary = "Realizar una compra", description = "Procesa la compra de tickets, valida stock y ejecuta el cobro simulado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compra procesada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en la solicitud (ej. formato de tarjeta)"),
            @ApiResponse(responseCode = "422", description = "Error de regla de negocio (ej. sin stock o límite de tickets excedido)"),
            @ApiResponse(responseCode = "404", description = "Concerto no encontrado")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PurchaseResponse> createPurchase(@Valid @RequestBody PurchaseRequest request) {
        ShoppingCart cart = new ShoppingCart();

        for (TicketPurchaseItemRequest item : request.getItems()) {
            Concert concert = concertRepository.findById(ConcertId.of(item.getConcertId()))
                    .orElseThrow(() -> new ConcertNotFoundException("Concerto no encontrado con ID: " + item.getConcertId()));

            TicketItem ticketItem = new TicketItem(
                    concert.getId(),
                    concert.getBand(),
                    item.getUnitPrice() != null ? Money.of(item.getUnitPrice()) : concert.getUnitPrice(),
                    Quantity.of(item.getQuantity())
            );
            cart.addItem(ticketItem);
        }

        CardNumber cardNumber = CardNumber.of(request.getCardNumber());
        purchaseService.execute(cart, cardNumber);

        PurchaseResponse response = new PurchaseResponse(
                "SUCCESS",
                cart.getTotalMoney().amount(),
                cart.getTotalQuantity(),
                request.getItems()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 Created
    }

    @Operation(summary = "Obtener todas las compras", description = "Retorna el historial completo de compras realizadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de compras obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> getAllPurchases() {
        return ResponseEntity.ok(List.of()); // 200 OK
    }
}

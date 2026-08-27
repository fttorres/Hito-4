package com.neonpulse.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * DTO que representa un ítem de ticket dentro de la solicitud de compra.
 */

@Schema(description = "DTO para los ítems de compra de tickets")
public class TicketPurchaseItemRequest {

    @NotBlank(message = "El ID del concerto es obligatorio")
    private String concertId;

    @Min(value = 1, message = "La cantidad debe ser de al menos 1 ticket")
    private int quantity;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio unitario debe ser positivo")
    private BigDecimal unitPrice;

    public TicketPurchaseItemRequest() {
    }

    public TicketPurchaseItemRequest(String concertId, int quantity, BigDecimal unitPrice) {
        this.concertId = concertId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getConcertId() {
        return concertId;
    }

    public void setConcertId(String concertId) {
        this.concertId = concertId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}

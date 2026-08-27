package com.neonpulse.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * DTO para procesar una compra de tickets.
 */

@Schema(description = "DTO para solicitud de compra de tickets")
public class PurchaseRequest {

    @NotBlank(message = "El número de tarjeta no puede estar vacío")
    private String cardNumber;

    @NotEmpty(message = "La lista de tickets a comprar no puede estar vacía")
    @Valid
    private List<TicketPurchaseItemRequest> items;

    public PurchaseRequest() {
    }

    public PurchaseRequest(String cardNumber, List<TicketPurchaseItemRequest> items) {
        this.cardNumber = cardNumber;
        this.items = items;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public List<TicketPurchaseItemRequest> getItems() {
        return items;
    }

    public void setItems(List<TicketPurchaseItemRequest> items) {
        this.items = items;
    }
}

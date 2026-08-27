package com.neonpulse.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta para compras exitosas.
 */

@Schema(description = "DTO de respuesta de una compra procesada")
public class PurchaseResponse {

    private String status;
    private BigDecimal totalAmount;
    private int totalQuantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private List<TicketPurchaseItemRequest> items;

    public PurchaseResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public PurchaseResponse(String status, BigDecimal totalAmount, int totalQuantity, List<TicketPurchaseItemRequest> items) {
        this.status = status;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.items = items;
        this.timestamp = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<TicketPurchaseItemRequest> getItems() {
        return items;
    }

    public void setItems(List<TicketPurchaseItemRequest> items) {
        this.items = items;
    }
}

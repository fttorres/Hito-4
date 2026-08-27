package com.neonpulse.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * Entidad JPA para la persistencia relacional de Concertos en la tabla 'concerts'.
 * Desacoplada del modelo de Dominio puro.
 */
@Entity
@Table(name = "concerts")
public class ConcertEntity {

    @Id
    @Column(name = "id", length = 64, nullable = false)
    private String id;

    @Column(name = "band", nullable = false, length = 255)
    private String band;
    
    @Column(name = "concert_date")
    private String date;
    
    @Column(name = "status")
    private String status;

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "available_stock", nullable = false)
    private int availableStock;

    public ConcertEntity() {
    }

    public ConcertEntity(String id, String band, String date, String status, BigDecimal unitPrice, int availableStock) {
        this.id = id;
        this.band = band;
        this.date = date;
        this.status = status;
        this.unitPrice = unitPrice;
        this.availableStock = availableStock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }
}

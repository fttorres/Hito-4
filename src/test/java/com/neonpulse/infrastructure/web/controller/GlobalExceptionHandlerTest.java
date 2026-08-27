package com.neonpulse.infrastructure.web.controller;

import com.neonpulse.domain.exception.ConcertInactiveException;
import com.neonpulse.domain.exception.ConcertNotFoundException;
import com.neonpulse.domain.exception.InvalidQuantityException;
import com.neonpulse.domain.exception.OutOfStockException;
import com.neonpulse.domain.exception.ResourceNotFoundException;
import com.neonpulse.infrastructure.web.dto.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("handleNotFound devuelve 404 y ErrorResponse estructurado")
    void handleNotFound_ShouldReturn404() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Recurso no encontrado");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("RESOURCE_NOT_FOUND", response.getBody().getError());
        assertEquals("Recurso no encontrado", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    @DisplayName("handleNotFound con ConcertNotFoundException devuelve 404")
    void handleNotFound_WithConcertNotFound_ShouldReturn404() {
        ConcertNotFoundException ex = new ConcertNotFoundException("Concerto inexistente");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("RESOURCE_NOT_FOUND", response.getBody().getError());
    }

    @Test
    @DisplayName("handleBusinessRuleViolation con OutOfStockException devuelve 422")
    void handleBusinessRuleViolation_OutOfStock_ShouldReturn422() {
        OutOfStockException ex = new OutOfStockException("Sin stock suficiente");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleBusinessRuleViolation(ex);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(422, response.getBody().getStatus());
        assertEquals("BUSINESS_RULE_VIOLATION", response.getBody().getError());
        assertEquals("Sin stock suficiente", response.getBody().getMessage());
    }

    @Test
    @DisplayName("handleBusinessRuleViolation con ConcertInactiveException devuelve 422")
    void handleBusinessRuleViolation_ConcertInactive_ShouldReturn422() {
        ConcertInactiveException ex = new ConcertInactiveException("El concierto ya finalizó");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleBusinessRuleViolation(ex);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(422, response.getBody().getStatus());
        assertEquals("BUSINESS_RULE_VIOLATION", response.getBody().getError());
    }

    @Test
    @DisplayName("handleBadRequest devuelve 400 y ErrorResponse")
    void handleBadRequest_ShouldReturn400() {
        InvalidQuantityException ex = new InvalidQuantityException("Cantidad inválida");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleBadRequest(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("BAD_REQUEST", response.getBody().getError());
    }

    @Test
    @DisplayName("handleGeneralException devuelve 500 sanitizado sin trazas nativas")
    void handleGeneralException_ShouldReturn500Sanitized() {
        Exception ex = new NullPointerException("Null reference inside internal server code");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGeneralException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
        assertEquals("INTERNAL_SERVER_ERROR", response.getBody().getError());
        assertEquals("Ha ocurrido un error interno en el servidor", response.getBody().getMessage());
    }
}

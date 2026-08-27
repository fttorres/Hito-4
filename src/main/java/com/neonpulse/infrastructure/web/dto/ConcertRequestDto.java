package com.neonpulse.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Pyl")
public record ConcertRequestDto(
        @NotBlank(message = "Concert ID is required")
        String id,

        @NotBlank(message = "Band name is required")
        String band,

        @NotBlank(message = "Date is required")
        String date,

        String status,

        @NotNull(message = "Total tickets is required")
        @Min(value = 1, message = "Total tickets must be at least 1")
        Integer totalTickets
) {}

package com.neonpulse.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de respuesta con los datos de un concierto")
public record ConcertResponseDto(
        String id,
        String band,
        String date,
        String status,
        Integer totalTickets
) {}

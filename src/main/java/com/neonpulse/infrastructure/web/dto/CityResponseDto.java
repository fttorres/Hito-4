package com.neonpulse.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de respuesta con los datos de la ciudad")
public record CityResponseDto(
        String id,
        String name
) {}

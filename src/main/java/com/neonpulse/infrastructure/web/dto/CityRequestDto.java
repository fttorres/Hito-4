package com.neonpulse.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


@Schema(description = "DTO para la creación/actualización de una ciudad")
public record CityRequestDto(
        @NotBlank(message = "City ID is required")
        String id,

        @NotBlank(message = "City name is required")
        String name
) {}

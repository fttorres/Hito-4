package com.neonpulse.infrastructure.web.controller;

import com.neonpulse.domain.entity.City;
import com.neonpulse.domain.exception.CityNotFoundException;
import com.neonpulse.application.service.CityService;
import com.neonpulse.infrastructure.web.dto.CityRequestDto;
import com.neonpulse.infrastructure.web.dto.CityResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cities")
@Tag(name = "Cities", description = "Operaciones relacionadas con las ciudades")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @Operation(summary = "Obtener todas las ciudades", description = "Devuelve una lista de todas las ciudades registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ciudades obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron ciudades"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<CityResponseDto> getAllCities() {
        return cityService.getAllCities().stream()
                .map(this::toResponse)
                .toList();
    }

    @Operation(summary = "Obtener ciudad por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ciudad encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CityResponseDto> getCityById(@PathVariable String id) {
        City city = cityService.getCityById(id)
                .orElseThrow(() -> new CityNotFoundException("Ciudad no encontrada con ID: " + id));
        return ResponseEntity.ok(toResponse(city));
    }

    @Operation(summary = "Crear una nueva ciudad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ciudad registrada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CityResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CityResponseDto> createCity(@Valid @RequestBody CityRequestDto request) {
        City city = new City(request.id(), request.name());
        cityService.saveCity(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(city));
    }

    @Operation(summary = "Actualizar una ciudad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ciudad actualizada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CityResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CityResponseDto> updateCity(@PathVariable String id, @Valid @RequestBody CityRequestDto request) {
        cityService.getCityById(id)
                .orElseThrow(() -> new CityNotFoundException("Ciudad no encontrada con ID: " + id));

        City updatedCity = new City(id, request.name());
        cityService.saveCity(updatedCity);
        return ResponseEntity.ok(toResponse(updatedCity));
    }

    @Operation(summary = "Eliminar ciudad", description = "Elimina una ciudad existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ciudad eliminada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable(name = "id") String id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

    private CityResponseDto toResponse(City city) {
        return new CityResponseDto(city.id(), city.name());
    }
}

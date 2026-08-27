package com.neonpulse.infrastructure.web.controller;

import com.neonpulse.domain.entity.Concert;
import com.neonpulse.domain.exception.ConcertNotFoundException;
import com.neonpulse.domain.repository.ConcertRepository;
import com.neonpulse.domain.valueobject.ConcertId;
import com.neonpulse.domain.valueobject.Money;
import com.neonpulse.infrastructure.web.dto.ConcertRequestDto;
import com.neonpulse.infrastructure.web.dto.ConcertResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST semántico para la gestión de concertos bajo la ruta versionada /api/v1/concerts.
 */
@RestController
@RequestMapping("/api/v1/concerts")
@Tag(name = "Cartelera", description = "Operaciones para la gestión de concertos en vivo")
public class ConcertController {

    private final ConcertRepository concertRepository;

    public ConcertController(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    @Operation(summary = "Obtener todos los concertos", description = "Retorna una lista con todos los concertos disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de concertos obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<ConcertResponseDto>> getAllConcerts() {
        List<ConcertResponseDto> response = concertRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response); // 200 OK
    }

    @Operation(summary = "Obtener concerto por ID", description = "Retorna los detalles de un concerto específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concerto encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Concerto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConcertResponseDto> getConcertById(@PathVariable String id) {
        Concert concert = concertRepository.findById(ConcertId.of(id))
                .orElseThrow(() -> new ConcertNotFoundException("Concerto no encontrado con ID: " + id));
        return ResponseEntity.ok(toResponse(concert)); // 200 OK
    }

    @Operation(summary = "Crear un nuevo concerto", description = "Registra un concerto en la base de datos PostgreSQL y retorna el recurso creado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Concerto registrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConcertResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en la solicitud")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ConcertResponseDto> createConcert(@Valid @RequestBody ConcertRequestDto request) {
        Concert concert = new Concert(
                ConcertId.of(request.id()),
                request.band(),
                request.date(),
                request.status(),
                Money.of(new java.math.BigDecimal("100.00")), // Default price since DTO doesn't provide it
                request.totalTickets()
        );
        concertRepository.save(concert);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(concert)); // 201 Created
    }

    @Operation(summary = "Actualizar un concerto", description = "Actualiza los datos de un concerto existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concerto actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConcertResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Concerto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ConcertResponseDto> updateConcert(@PathVariable String id, @Valid @RequestBody ConcertRequestDto request) {
        ConcertId concertId = ConcertId.of(id);
        Concert existingConcert = concertRepository.findById(concertId)
                .orElseThrow(() -> new ConcertNotFoundException("Concerto no encontrado con ID: " + id));

        Concert updatedConcert = new Concert(
                concertId,
                request.band(),
                request.date(),
                request.status(),
                existingConcert.getUnitPrice(), // Keep existing price
                request.totalTickets()
        );
        concertRepository.save(updatedConcert);
        return ResponseEntity.ok(toResponse(updatedConcert)); // 200 OK
    }

    @Operation(summary = "Eliminar un concerto", description = "Elimina un concerto de la base de datos de manera física.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Concerto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Concerto no encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteConcert(@PathVariable String id) {
        ConcertId concertId = ConcertId.of(id);
        concertRepository.findById(concertId)
                .orElseThrow(() -> new ConcertNotFoundException("Concerto no encontrado con ID: " + id));

        concertRepository.deleteById(concertId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    private ConcertResponseDto toResponse(Concert concert) {
        return new ConcertResponseDto(
                concert.getId().value(),
                concert.getBand(),
                concert.getDate(),
                concert.getStatus(),
                concert.getAvailableStock()
        );
    }
}

package com.veranum.corehabitacion.controller;

import com.veranum.corehabitacion.dto.ApiResponse;
import com.veranum.corehabitacion.dto.EstadoHabitacionRequest;
import com.veranum.corehabitacion.dto.HabitacionRequest;
import com.veranum.corehabitacion.dto.HabitacionResponse;
import com.veranum.corehabitacion.service.HabitacionService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    public HabitacionController(
            HabitacionService habitacionService) {

        this.habitacionService = habitacionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HabitacionResponse>>
    crear(
            @Valid @RequestBody HabitacionRequest request) {

        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(
                new ApiResponse<>(
                        201,
                        "Habitación creada correctamente",
                        false,
                        habitacionService.crear(request)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HabitacionResponse>>>
    obtenerTodas() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Listado de habitaciones",
                        false,
                        habitacionService.obtenerTodas()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HabitacionResponse>>
    obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Habitación encontrada",
                        false,
                        habitacionService.obtenerPorId(id)
                )
        );
    }

    @GetMapping("/disponibles")
    public ResponseEntity<ApiResponse<List<HabitacionResponse>>>
    obtenerDisponibles() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Habitaciones disponibles",
                        false,
                        habitacionService.obtenerDisponibles()
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HabitacionResponse>>
    actualizar(
            @PathVariable Long id,
            @Valid @RequestBody HabitacionRequest request) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Habitación actualizada correctamente",
                        false,
                        habitacionService.actualizar(
                                id,
                                request
                        )
                )
        );
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<HabitacionResponse>>
    actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody EstadoHabitacionRequest request) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Estado de habitación actualizado",
                        false,
                        habitacionService.actualizarEstado(
                                id,
                                request
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>>
    eliminar(
            @PathVariable Long id) {

        habitacionService.eliminar(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Habitación eliminada correctamente",
                        false,
                        null
                )
        );
    }
}

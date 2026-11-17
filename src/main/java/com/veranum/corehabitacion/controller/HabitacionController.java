package com.veranum.corehabitacion.controller;

import com.veranum.corehabitacion.dto.ApiResponse;
import com.veranum.corehabitacion.dto.EstadoHabitacionRequest;
import com.veranum.corehabitacion.dto.HabitacionRequest;
import com.veranum.corehabitacion.dto.HabitacionResponse;
import com.veranum.corehabitacion.service.HabitacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
        name = "Gestión de Habitaciones",
        description = "Operaciones para crear, consultar, actualizar, cambiar estado y eliminar habitaciones del hotel"
)

@RestController
@RequestMapping("/api/v1/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    public HabitacionController(
            HabitacionService habitacionService) {

        this.habitacionService = habitacionService;
    }



        @Operation(
        summary = "Crear una habitación",
        description = "Registra una nueva habitación indicando número, tipo, precio por noche y estado."
)
        @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Habitación creada correctamente"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Datos inválidos o número de habitación duplicado"
        )
})

    
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

        @Operation(
        summary = "Listar habitaciones",
        description = "Obtiene todas las habitaciones registradas en el sistema."
)
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Listado obtenido correctamente"
)


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


        @Operation(
        summary = "Buscar habitación por ID",
        description = "Obtiene los datos de una habitación específica mediante su identificador."
)
        @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Habitación encontrada"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Habitación no encontrada"
        )
})

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

        @Operation(
        summary = "Listar habitaciones disponibles",
        description = "Obtiene únicamente las habitaciones cuyo estado es DISPONIBLE."
)
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Habitaciones disponibles obtenidas correctamente"
)


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
    
        @Operation(
        summary = "Actualizar habitación",
        description = "Actualiza el número, tipo, precio por noche y estado de una habitación existente."
)
        @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Habitación actualizada correctamente"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Datos inválidos o número duplicado"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Habitación no encontrada"
        )
})

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

        @Operation(
        summary = "Cambiar estado de habitación",
        description = "Modifica únicamente el estado de una habitación: DISPONIBLE, OCUPADA o MANTENIMIENTO."
)
        @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Estado actualizado correctamente"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Estado inválido"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Habitación no encontrada"
        )
})


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

        @Operation(
        summary = "Eliminar habitación",
        description = "Elimina una habitación registrada mediante su identificador."
)
        @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Habitación eliminada correctamente"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Habitación no encontrada"
        )
})    


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

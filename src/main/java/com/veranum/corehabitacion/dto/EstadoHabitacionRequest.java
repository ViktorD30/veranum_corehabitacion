package com.veranum.corehabitacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class EstadoHabitacionRequest {

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
            regexp = "^(DISPONIBLE|OCUPADA|MANTENIMIENTO)$",
            message = "El estado debe ser DISPONIBLE, OCUPADA o MANTENIMIENTO"
    )
    private String estado;

    public EstadoHabitacionRequest() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

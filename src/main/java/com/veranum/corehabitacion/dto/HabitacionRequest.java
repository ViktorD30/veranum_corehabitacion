package com.veranum.corehabitacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class HabitacionRequest {

    @NotBlank(message = "El número de habitación es obligatorio")
    private String numero;

    @NotBlank(message = "El tipo de habitación es obligatorio")
    @Pattern(
            regexp = "^(SIMPLE|DOBLE|FAMILIAR)$",
            message = "El tipo debe ser SIMPLE, DOBLE o FAMILIAR"
    )
    private String tipo;

    @NotNull(message = "El precio por noche es obligatorio")
    @Positive(message = "El precio por noche debe ser mayor a 0")
    private Double precioPorNoche;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
            regexp = "^(DISPONIBLE|OCUPADA|MANTENIMIENTO)$",
            message = "El estado debe ser DISPONIBLE, OCUPADA o MANTENIMIENTO"
    )
    private String estado;

    public HabitacionRequest() {
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(Double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
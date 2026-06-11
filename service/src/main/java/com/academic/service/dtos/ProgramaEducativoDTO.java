package com.academic.service.dtos;

import com.academic.service.entities.ProgramaEducativo.Modalidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para crear o actualizar un ProgramaEducativo.
 */
public class ProgramaEducativoDTO {

    @NotBlank(message = "El nombre del programa es obligatorio")
    private String nombre;

    @NotNull(message = "La modalidad es obligatoria")
    private Modalidad modalidad;

    @NotNull(message = "El ID de la division es obligatorio")
    private Long divisionId;

    // ---------- Getters y Setters ----------

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Modalidad getModalidad() { return modalidad; }
    public void setModalidad(Modalidad modalidad) { this.modalidad = modalidad; }

    public Long getDivisionId() { return divisionId; }
    public void setDivisionId(Long divisionId) { this.divisionId = divisionId; }
}

package com.academic.service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramaCreateRequestDTO {

    @NotBlank(message = "El nombre del programa es obligatorio")
    private String nombre;

    @NotBlank(message = "La modalidad del programa es obligatoria")
    @Pattern(regexp = "^(Intensiva|Mixta)$", message = "La modalidad debe ser 'Intensiva' o 'Mixta'")
    private String modalidad;

    @NotNull(message = "El ID de la división padre es obligatorio")
    private Long divisionId; 
}

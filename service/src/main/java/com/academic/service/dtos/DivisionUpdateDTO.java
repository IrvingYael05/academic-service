package com.academic.service.dtos;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DivisionUpdateDTO {
    
    @NotBlank(message = "El nombre de la división es obligatorio")
    private String nombre;
    
    private Boolean activo;
}
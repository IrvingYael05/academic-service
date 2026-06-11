package com.academic.service.dtos;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DivisionDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre de la división es obligatorio")
    private String nombre;
    
    @NotBlank(message = "La clave es obligatoria")
    private String clave;
    
    private Boolean activo;
}

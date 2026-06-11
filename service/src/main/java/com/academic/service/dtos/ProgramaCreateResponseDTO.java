package com.academic.service.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProgramaCreateResponseDTO {
    private Long id;
    private String nombre;
    private String modalidad;
    private Long divisionId;
}

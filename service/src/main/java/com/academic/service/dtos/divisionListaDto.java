package com.academic.service.dtos; // Ajusta el package según tu estructura
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class divisionListaDto {
    private Long id; // Es recomendable incluir el ID para que Angular sepa a qué división redirigir en "Ver Programas"
    private String nombre;
    private Boolean activo;
}
package com.academic.service.services;

import com.academic.service.dtos.ProgramaCreateRequestDTO;
import com.academic.service.dtos.ProgramaCreateResponseDTO;
import com.academic.service.entities.Division;
import com.academic.service.entities.ProgramaEducativo;
import com.academic.service.repositories.DivisionRepository;
import com.academic.service.repositories.ProgramaEducativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgramaRegistrationService {

    private final ProgramaEducativoRepository programaRepository;
    private final DivisionRepository divisionRepository;

    @Autowired
    public ProgramaRegistrationService(ProgramaEducativoRepository programaRepository, DivisionRepository divisionRepository) {
        this.programaRepository = programaRepository;
        this.divisionRepository = divisionRepository;
    }

    public ProgramaCreateResponseDTO registrarPrograma(ProgramaCreateRequestDTO requestDTO) {
        
        // 1. Buscar la division padre
        Division division = divisionRepository.findById(requestDTO.getDivisionId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la división con ID: " + requestDTO.getDivisionId()));

        // 2. Mapear los datos a la Entidad
        ProgramaEducativo nuevoPrograma = ProgramaEducativo.builder()
                .nombre(requestDTO.getNombre())
                .modalidad(ProgramaEducativo.Modalidad.valueOf(requestDTO.getModalidad().toUpperCase()))
                .division(division)
                .build();

        // 3. Guardar en la base de datos
        ProgramaEducativo programaGuardado = programaRepository.save(nuevoPrograma);

        // 4. Retornar el DTO de respuesta
        return ProgramaCreateResponseDTO.builder()
                .id(programaGuardado.getId())
                .nombre(programaGuardado.getNombre())
                .modalidad(programaGuardado.getModalidad().name())
                .divisionId(programaGuardado.getDivision().getId())
                .build();
    }
}

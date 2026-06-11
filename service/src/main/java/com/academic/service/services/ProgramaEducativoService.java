package com.academic.service.services;

import com.academic.service.dtos.ProgramaEducativoDTO;
import com.academic.service.entities.Division;
import com.academic.service.entities.ProgramaEducativo;
import com.academic.service.repositories.DivisionRepository;
import com.academic.service.repositories.ProgramaEducativoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProgramaEducativoService {

    private final ProgramaEducativoRepository programaRepo;
    private final DivisionRepository divisionRepo;

    public ProgramaEducativoService(ProgramaEducativoRepository programaRepo,
                                    DivisionRepository divisionRepo) {
        this.programaRepo = programaRepo;
        this.divisionRepo = divisionRepo;
    }

    /** Lista todos los programas. */
    @Transactional(readOnly = true)
    public List<ProgramaEducativo> listarTodos() {
        return programaRepo.findAll();
    }

    /** Lista programas por division. */
    @Transactional(readOnly = true)
    public List<ProgramaEducativo> listarPorDivision(Long divisionId) {
        return programaRepo.findByDivisionId(divisionId);
    }

    /** Obtiene un programa por ID o lanza excepcion. */
    @Transactional(readOnly = true)
    public ProgramaEducativo obtenerPorId(Long id) {
        return programaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Programa no encontrado con id: " + id));
    }

    /** Crea un nuevo programa educativo. */
    @Transactional
    public ProgramaEducativo crear(ProgramaEducativoDTO dto) {
        Division division = divisionRepo.findById(dto.getDivisionId())
                .orElseThrow(() -> new RuntimeException("Division no encontrada con id: " + dto.getDivisionId()));

        ProgramaEducativo programa = ProgramaEducativo.builder()
                .nombre(dto.getNombre())
                .modalidad(dto.getModalidad())
                .division(division)
                .build();

        return programaRepo.save(programa);
    }

    /** Actualiza nombre, modalidad y/o division de un programa existente. */
    @Transactional
    public ProgramaEducativo actualizar(Long id, ProgramaEducativoDTO dto) {
        ProgramaEducativo programa = obtenerPorId(id);

        Division division = divisionRepo.findById(dto.getDivisionId())
                .orElseThrow(() -> new RuntimeException("Division no encontrada con id: " + dto.getDivisionId()));

        programa.setNombre(dto.getNombre());
        programa.setModalidad(dto.getModalidad());
        programa.setDivision(division);

        return programaRepo.save(programa);
    }

    /** Elimina un programa por ID. */
    @Transactional
    public void eliminar(Long id) {
        if (!programaRepo.existsById(id)) {
            throw new RuntimeException("Programa no encontrado con id: " + id);
        }
        programaRepo.deleteById(id);
    }
}

package com.academic.service.services;

import com.academic.service.dtos.DivisionDTO;
import com.academic.service.dtos.DivisionUpdateDTO;
import com.academic.service.entities.Division;
import com.academic.service.exceptions.DivisionClaveAlreadyExistsException;
import com.academic.service.exceptions.DivisionNotFoundException;
import com.academic.service.repositories.DivisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DivisionService {
    
    private final DivisionRepository divisionRepository;
    
    public List<DivisionDTO> obtenerTodasLasDivisiones() {
        return divisionRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    public DivisionDTO obtenerDivisionPorId(Long id) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new DivisionNotFoundException(id));
        return convertirADTO(division);
    }
    
    @Transactional
    public DivisionDTO crearDivision(DivisionDTO divisionDTO) {
        if (divisionRepository.existsByClave(divisionDTO.getClave())) {
            throw new DivisionClaveAlreadyExistsException(divisionDTO.getClave());
        }
        
        Division division = Division.builder()
                .nombre(divisionDTO.getNombre())
                .clave(divisionDTO.getClave())
                .activo(true)
                .build();
        
        Division divisionGuardada = divisionRepository.save(division);
        return convertirADTO(divisionGuardada);
    }
    
    @Transactional
    public DivisionDTO actualizarDivision(Long id, DivisionUpdateDTO divisionUpdateDTO) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new DivisionNotFoundException(id));
        
        if (divisionUpdateDTO.getNombre() != null && !divisionUpdateDTO.getNombre().isBlank()) {
            division.setNombre(divisionUpdateDTO.getNombre());
        }
        
        if (divisionUpdateDTO.getActivo() != null) {
            division.setActivo(divisionUpdateDTO.getActivo());
        }
        
        Division divisionActualizada = divisionRepository.save(division);
        return convertirADTO(divisionActualizada);
    }
    
    private DivisionDTO convertirADTO(Division division) {
        return DivisionDTO.builder()
                .id(division.getId())
                .nombre(division.getNombre())
                .clave(division.getClave())
                .activo(division.getActivo())
                .build();
    }
}

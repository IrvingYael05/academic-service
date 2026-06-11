package com.academic.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academic.service.dtos.divisionListaDto;
import com.academic.service.entities.Division;
import com.academic.service.repositories.DivisionRepository;

@RestController
@RequestMapping("/api/divisiones")
@CrossOrigin(origins = "*")
public class divisionController {

    @Autowired
    private DivisionRepository divisionRepository;

    @GetMapping
    public ResponseEntity<Page<divisionListaDto>> listarDivisiones(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        
        // 1. Buscamos las entidades paginadas desde la BD
        Page<Division> paginaDivisiones = divisionRepository.findAll(pageable);
        
        // 2. Mapeamos cada entidad al DTO correspondiente
        Page<divisionListaDto> paginaDtos = paginaDivisiones.map(division -> 
            divisionListaDto.builder()
                .id(division.getId())
                .nombre(division.getNombre())
                .activo(division.getActivo())
                .build()
        );
        
        // 3. Retornamos la página de DTOs con la metadata intacta
        return ResponseEntity.ok(paginaDtos);
    }
}
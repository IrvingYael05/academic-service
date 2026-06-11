package com.academic.service.controllers;

import com.academic.service.dtos.DivisionDTO;
import com.academic.service.dtos.DivisionUpdateDTO;
import com.academic.service.services.DivisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/divisiones")
@RequiredArgsConstructor
public class DivisionController {
    
    private final DivisionService divisionService;
    
    @GetMapping
    public ResponseEntity<List<DivisionDTO>> obtenerTodasLasDivisiones() {
        return ResponseEntity.ok(divisionService.obtenerTodasLasDivisiones());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DivisionDTO> obtenerDivisionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(divisionService.obtenerDivisionPorId(id));
    }

    @PostMapping
    public ResponseEntity<DivisionDTO> crearDivision(@Valid @RequestBody DivisionDTO divisionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(divisionService.crearDivision(divisionDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DivisionDTO> actualizarDivision(
            @PathVariable Long id,
            @Valid @RequestBody DivisionUpdateDTO divisionUpdateDTO) {
        return ResponseEntity.ok(divisionService.actualizarDivision(id, divisionUpdateDTO));
    }
}

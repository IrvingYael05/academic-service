package com.academic.service.controllers;

import com.academic.service.dtos.ProgramaCreateRequestDTO;
import com.academic.service.dtos.ProgramaCreateResponseDTO;
import com.academic.service.services.ProgramaRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/programas/registro")
@CrossOrigin(origins = "http://localhost:4200")
public class ProgramaRegistrationController {

    private final ProgramaRegistrationService registrationService;

    @Autowired
    public ProgramaRegistrationController(ProgramaRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<ProgramaCreateResponseDTO> registrarNuevoPrograma(
            @Valid @RequestBody ProgramaCreateRequestDTO requestDTO) {
        
        ProgramaCreateResponseDTO response = registrationService.registrarPrograma(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

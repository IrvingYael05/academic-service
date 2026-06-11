package com.academic.service.controllers;

import com.academic.service.dtos.ProgramaEducativoDTO;
import com.academic.service.entities.ProgramaEducativo;
import com.academic.service.services.ProgramaEducativoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programas")
public class ProgramaEducativoController {

    private final ProgramaEducativoService service;

    public ProgramaEducativoController(ProgramaEducativoService service) {
        this.service = service;
    }

    /** GET /api/programas  -> Lista todos */
    @GetMapping
    public ResponseEntity<List<ProgramaEducativo>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /** GET /api/programas/division/{divisionId}  -> Lista por division */
    @GetMapping("/division/{divisionId}")
    public ResponseEntity<List<ProgramaEducativo>> listarPorDivision(@PathVariable Long divisionId) {
        return ResponseEntity.ok(service.listarPorDivision(divisionId));
    }

    /** GET /api/programas/{id}  -> Obtiene uno */
    @GetMapping("/{id}")
    public ResponseEntity<ProgramaEducativo> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    /** POST /api/programas  -> Crea nuevo */
    @PostMapping
    public ResponseEntity<ProgramaEducativo> crear(@Valid @RequestBody ProgramaEducativoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    /** PUT /api/programas/{id}  -> Actualiza completo */
    @PutMapping("/{id}")
    public ResponseEntity<ProgramaEducativo> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody ProgramaEducativoDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    /** DELETE /api/programas/{id}  -> Elimina */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

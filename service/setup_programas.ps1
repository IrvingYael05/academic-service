# ============================================================
# Script: setup_programas.ps1
# Descripcion: Crea DTO, Service y Controller para ProgramaEducativo
# Ejecutar desde la raiz del proyecto SERVICE
# ============================================================

$base = "src\main\java\com\academic\service"

# ============================================================
# 1. DTO  ->  dtos\dto.java
# ============================================================
$dtoContent = @'
package com.academic.service.dtos;

import com.academic.service.entities.ProgramaEducativo.Modalidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para crear o actualizar un ProgramaEducativo.
 */
public class ProgramaEducativoDTO {

    @NotBlank(message = "El nombre del programa es obligatorio")
    private String nombre;

    @NotNull(message = "La modalidad es obligatoria")
    private Modalidad modalidad;

    @NotNull(message = "El ID de la division es obligatorio")
    private Long divisionId;

    // ---------- Getters y Setters ----------

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Modalidad getModalidad() { return modalidad; }
    public void setModalidad(Modalidad modalidad) { this.modalidad = modalidad; }

    public Long getDivisionId() { return divisionId; }
    public void setDivisionId(Long divisionId) { this.divisionId = divisionId; }
}
'@

Set-Content -Path "$base\dtos\dto.java" -Value $dtoContent -Encoding UTF8
Write-Host "[OK] dto.java creado/actualizado"

# ============================================================
# 2. SERVICE  ->  services\service.java
# ============================================================
$serviceContent = @'
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
'@

Set-Content -Path "$base\services\service.java" -Value $serviceContent -Encoding UTF8
Write-Host "[OK] service.java creado/actualizado"

# ============================================================
# 3. CONTROLLER  ->  controllers\controllers.java
# ============================================================
$controllerContent = @'
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
'@

Set-Content -Path "$base\controllers\controllers.java" -Value $controllerContent -Encoding UTF8
Write-Host "[OK] controllers.java creado/actualizado"

# ============================================================
Write-Host ""
Write-Host "============================================"
Write-Host " Archivos generados exitosamente"
Write-Host "============================================"
Write-Host " Endpoints disponibles:"
Write-Host "  GET    /api/programas"
Write-Host "  GET    /api/programas/{id}"
Write-Host "  GET    /api/programas/division/{divisionId}"
Write-Host "  POST   /api/programas"
Write-Host "  PUT    /api/programas/{id}"
Write-Host "  DELETE /api/programas/{id}"
Write-Host "============================================"

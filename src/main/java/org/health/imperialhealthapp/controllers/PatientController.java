package org.health.imperialhealthapp.controllers;

import org.health.imperialhealthapp.config.Executor;
import org.health.imperialhealthapp.config.GeneralResult;
import org.health.imperialhealthapp.models.dto.PatientDto;
import org.health.imperialhealthapp.services.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/patients")
public class PatientController {

    private final PatientService service;
    private final Executor executor;

    public PatientController(PatientService service) {
        this.service = service;
        this.executor = new Executor();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<GeneralResult<PatientDto>> findPatient(@PathVariable(value = "id") Integer id) {
        return executor.execute(() -> service.findPatient(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<GeneralResult<Void>> savePatient(@RequestBody PatientDto patientDto) {
        return executor.execute(() -> service.savePatient(patientDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<GeneralResult<Void>> deletePatient(@PathVariable(value = "id") Integer id) {
        return executor.execute(() -> service.deletePatient(id));
    }

}

package org.health.imperialhealthapp.controllers;

import org.health.imperialhealthapp.config.Executor;
import org.health.imperialhealthapp.config.GeneralResult;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.health.imperialhealthapp.services.ObservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/observations")
@RestController
public class ObservationController {

    private final ObservationService service;
    private final Executor executor;

    public ObservationController(ObservationService service) {
        executor = new Executor();
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GeneralResult<Page<ObservationDto>>> listAll(Pageable pageable) {
        return executor.execute(() -> service.listAll(pageable));
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GeneralResult<ObservationDto>> getById(@PathVariable(value = "id") String id) {
        return executor.execute(() -> service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GeneralResult<Void>> save(@RequestBody ObservationDto observationDto) {
        return executor.execute(() -> service.save(observationDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GeneralResult<Void>> update(@RequestBody ObservationDto observationDto,
                                                      @PathVariable(value = "id") String id) {
        return executor.execute(() -> service.update(observationDto, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GeneralResult<Void>> delete(@PathVariable(value = "id") String id) {
        return executor.execute(() -> service.delete(id));
    }
}

package org.health.imperialhealthapp.controllers;

import org.health.imperialhealthapp.config.Executor;
import org.health.imperialhealthapp.config.GeneralResult;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.health.imperialhealthapp.services.ObservationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/observations")
@RestController
public class ObservationController {

    private final ObservationService service;
    private final Executor executor;

    public ObservationController(ObservationService service) {
        executor = new Executor();
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<GeneralResult<Slice<ObservationDto>>> listAll(Pageable pageable) {
        return executor.execute(() -> service.listAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GeneralResult<ObservationDto>> getById(@PathVariable(value = "id") String id) {
        return executor.execute(() -> service.findById(id));
    }

    @PostMapping
    public ResponseEntity<GeneralResult<Void>> save(@RequestBody ObservationDto observationDto) {
        return executor.execute(() -> service.save(observationDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResult<Void>> update(@RequestBody ObservationDto observationDto,
                                                      @PathVariable(value = "id") String id) {
        return executor.execute(() -> service.update(observationDto, id));
    }

}

package org.health.imperialhealthapp.controllers;

import org.health.imperialhealthapp.models.Executor;
import org.health.imperialhealthapp.models.GeneralResult;
import org.health.imperialhealthapp.models.NetworkUtil;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.health.imperialhealthapp.services.ObservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Executable;
import java.util.List;

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
    public ResponseEntity<GeneralResult<List<ObservationDto>>> listAll() {
        return executor.execute(service::listAll);
    }

}

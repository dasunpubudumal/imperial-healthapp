package org.health.imperialhealthapp.controllers;

import org.health.imperialhealthapp.config.Executable;
import org.health.imperialhealthapp.config.Executor;
import org.health.imperialhealthapp.config.GeneralResult;
import org.health.imperialhealthapp.models.domain.MeasurementType;
import org.health.imperialhealthapp.services.MeasurementTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/measurement-types")
public class MeasurementTypeController {

    private final MeasurementTypeService measurementTypeService;
    private final Executor executor;

    public MeasurementTypeController(MeasurementTypeService measurementTypeService) {
        this.measurementTypeService = measurementTypeService;
        this.executor = new Executor();
    }

    @GetMapping
    public ResponseEntity<GeneralResult<List<MeasurementType>>> findAll() {
        return executor.execute(measurementTypeService::findAllMeasurements);
    }
}

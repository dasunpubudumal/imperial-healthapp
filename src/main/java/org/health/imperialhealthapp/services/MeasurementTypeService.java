package org.health.imperialhealthapp.services;

import lombok.extern.slf4j.Slf4j;
import org.health.imperialhealthapp.config.GeneralResult;
import org.health.imperialhealthapp.exceptions.InternalServerException;
import org.health.imperialhealthapp.models.Status;
import org.health.imperialhealthapp.models.domain.MeasurementType;
import org.health.imperialhealthapp.repositories.MeasurementTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MeasurementTypeService {

    private final MeasurementTypeRepository repository;

    public MeasurementTypeService(MeasurementTypeRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<GeneralResult<List<MeasurementType>>> findAllMeasurements() {
        log.info("Retrieving measurement types..");
        try {
            List<MeasurementType> measurementTypes = new ArrayList<>();
            repository.findAll().forEach(measurementTypes::add);
            return ResponseEntity.ok(
                    GeneralResult.<List<MeasurementType>>builder().data(measurementTypes).status(Status.SUCCESS).build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerException("Error in fetching measurement types");
        }
    }

}

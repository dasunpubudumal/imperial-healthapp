package org.health.imperialhealthapp.services;

import lombok.extern.slf4j.Slf4j;
import org.health.imperialhealthapp.exceptions.InternalServerException;
import org.health.imperialhealthapp.exceptions.InvalidRequestException;
import org.health.imperialhealthapp.mapper.ObservationMapper;
import org.health.imperialhealthapp.config.GeneralResult;
import org.health.imperialhealthapp.models.Status;
import org.health.imperialhealthapp.models.domain.MeasurementType;
import org.health.imperialhealthapp.models.domain.Observation;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.health.imperialhealthapp.repositories.MeasurementTypeRepository;
import org.health.imperialhealthapp.repositories.ObservationFetchRepository;
import org.health.imperialhealthapp.repositories.ObservationRepository;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ObservationService {

    private final ObservationRepository repository;
    private final MeasurementTypeRepository measurementTypeRepository;
    private final ObservationFetchRepository fetchRepository;

    public ObservationService(ObservationRepository repository, MeasurementTypeRepository measurementTypeRepository,
                              ObservationFetchRepository fetchRepository) {
        this.repository = repository;
        this.measurementTypeRepository = measurementTypeRepository;
        this.fetchRepository = fetchRepository;
    }

    /**
     * Lists all observations
     * @param pageable paginated page
     * @return paginated list of observations
     * Slice is only aware of whether there are more results or not.
     * This prevents the database O/H to query the count of total results fitting to the query criteria.
     */
    @Transactional
    public ResponseEntity<GeneralResult<Page<ObservationDto>>> listAll(Pageable pageable) {
        return ResponseEntity.ok(
                GeneralResult.<Page<ObservationDto>>builder().status(Status.SUCCESS).data(
                        fetchRepository.findAll(pageable).map(ObservationMapper.INSTANCE::convertToDto)
                ).build()
        );
    }

    /**
     * Returns observation by ID
     * @param id observation ID
     * @return observation wrapped with parameterized response entity
     */
    @Transactional
    public ResponseEntity<GeneralResult<ObservationDto>> findById(String id) {
        if (Objects.isNull(id)) {
            log.error("ID {} not found in the request.", id);
            throw new InvalidRequestException(
                    "ID not found in the request!"
            );
        }
        Observation observation = repository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new InvalidRequestException(
                String.format("No observation found for ID: %s", id)
        ));
        ObservationDto convertedToDto = ObservationMapper.INSTANCE.convertToDto(observation);
        if (Objects.isNull(convertedToDto)) {
            log.error("DTO Conversion failed.");
            throw new InternalServerException();
        }
        return ResponseEntity.ok(
                GeneralResult.<ObservationDto>builder()
                        .data(convertedToDto)
                        .status(Status.SUCCESS).
                        build()
        );
    }

    /**
     * Persists an observation
     * @param observationDto observation
     * @return response wrapped
     */
    @Transactional
    public ResponseEntity<GeneralResult<Void>> save(ObservationDto observationDto) {
        if (Objects.isNull(observationDto)) {
            throw new InvalidRequestException("No observation found in the request body.");
        }
        try {
            Observation convert = ObservationMapper.INSTANCE.convert(observationDto);
            MeasurementType measurementType = measurementTypeRepository
                    .findByMeasurementType(observationDto.getMeasurementType())
                    .orElseThrow(
                            () -> new InvalidRequestException("Measurement type not found")
            );
            convert.setMeasurementType(measurementType);
            return ResponseEntity.ok(
                    GeneralResult.<Void>builder().status(Status.SUCCESS).build()
            );
        } catch (Exception e) {
            throw new InvalidRequestException("Error in the observation body.");
        }
    }

    /**
     * Updates a given observation
     * @param observationDto new observation
     * @param id given observation ID
     * @return response wrapped
     */
    @Transactional
    public ResponseEntity<GeneralResult<Void>> update(ObservationDto observationDto, String id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException(
                    "ID not found in the request!"
            );
        }
        try {
            repository
                    .findById(UUID.fromString(id))
                    .orElseThrow(() -> new InvalidRequestException(
                            String.format("No observation found for ID: %s", id)
                    ));
            Observation convert = ObservationMapper.INSTANCE.convert(observationDto);
            convert.setId(UUID.fromString(id));
            return ResponseEntity.ok(
                    GeneralResult.<Void>builder().status(Status.SUCCESS).build()
            );
        } catch (Exception e) {
            throw new InternalServerException();
        }
    }

    /**
     * Deletes an observation given by ID
     * @param id observation ID
     * @return response wrapped
     */
    @Transactional
    public ResponseEntity<GeneralResult<Void>> delete(String id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException(
                    "ID not found in the request!"
            );
        }
        try {
            Observation observation = repository
                    .findById(UUID.fromString(id))
                    .orElseThrow(() -> new InvalidRequestException(
                            String.format("No observation found for ID: %s", id)
                    ));
            repository.delete(observation);
            return ResponseEntity.ok(
                    GeneralResult.<Void>builder().status(Status.SUCCESS).build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerException();
        }
    }

}

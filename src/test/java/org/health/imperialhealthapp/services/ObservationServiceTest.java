package org.health.imperialhealthapp.services;

import org.health.imperialhealthapp.config.GeneralResult;
import org.health.imperialhealthapp.models.domain.MeasurementType;
import org.health.imperialhealthapp.models.domain.Observation;
import org.health.imperialhealthapp.models.domain.Patient;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.health.imperialhealthapp.repositories.MeasurementTypeRepository;
import org.health.imperialhealthapp.repositories.ObservationFetchRepository;
import org.health.imperialhealthapp.repositories.ObservationRepository;
import org.health.imperialhealthapp.util.DateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ObservationServiceTest {

    ObservationRepository repository = mock(
            ObservationRepository.class
    );

    MeasurementTypeRepository measurementTypeRepository = mock(
            MeasurementTypeRepository.class
    );

    ObservationFetchRepository fetchRepository = mock(
            ObservationFetchRepository.class
    );

    String uuid = UUID.randomUUID().toString();

    ObservationService service;

    @BeforeEach
    void setUp() {
        when(fetchRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(
                                Observation.builder()
                                        .id(UUID.fromString(uuid))
                                        .measurementType(MeasurementType.builder().measurementType("x").unit("y").build())
                                        .date(DateMapper.asDate("2023-09-05T15:12:23Z"))
                                        .patient(Patient.builder().id(1).build())
                                        .value(10.2)
                                        .build()
                        )
                ));
        when(repository.findById(any())).thenReturn(
                Optional.ofNullable(Observation.builder()
                        .id(UUID.fromString(uuid))
                        .measurementType(MeasurementType.builder().measurementType("x").unit("y").build())
                        .date(DateMapper.asDate("2023-09-05T15:12:23Z"))
                        .patient(Patient.builder().id(1).build())
                        .value(10.2)
                        .build())
        );
        when(measurementTypeRepository.findByMeasurementType(any()))
                .thenReturn(
                        Optional.of(MeasurementType.builder().measurementType("rate").unit("ss").build())
                );
        when(repository.save(any())).thenReturn(
                Observation.builder()
                        .id(UUID.fromString(uuid))
                        .measurementType(MeasurementType.builder().measurementType("x").unit("y").build())
                        .date(DateMapper.asDate("2023-09-05T15:12:23Z"))
                        .patient(Patient.builder().id(1).build())
                        .value(10.2)
                        .build()
        );
        service = new ObservationService(repository, measurementTypeRepository, fetchRepository);
    }

    @Test
    @DisplayName("Check listing all observations")
    void listAll() {
        ResponseEntity<GeneralResult<Page<ObservationDto>>> generalResultResponseEntity = service.listAll(
                PageRequest.of(0, 1)
        );
        assertTrue(Objects.nonNull(generalResultResponseEntity.getBody()));
        assertEquals(generalResultResponseEntity.getBody().getData().getContent().size(), 1);
    }

    @Test
    @DisplayName("Check unit tests for finding by ID")
    void getById() {
        ResponseEntity<GeneralResult<ObservationDto>> byId = service.findById(uuid);
        assertTrue(Objects.nonNull(byId.getBody()));
        assertEquals(
                uuid,
                byId.getBody().getData().getId()
        );
    }

    @Test
    @DisplayName("Check unit tests for save")
    void save() {
        try {
            service.save(
                    ObservationDto.builder()
                            .id(uuid)
                            .measurementType("heart-rate")
                            .unit("beats/minute")
                            .date("2023-09-10T08:54:33Z")
                            .value(76.0d)
                            .patient(101)
                            .build()
            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @DisplayName("Check unit tests for update")
    void update() {
        try {
            service.update(
                    ObservationDto.builder()
                            .id(uuid)
                            .measurementType("heart-rate")
                            .unit("beats/minute")
                            .date("2023-09-10T08:54:33Z")
                            .value(76.0d)
                            .patient(101)
                            .build(),
                    uuid
            );
        } catch (Exception e) {
            fail();
        }
    }
}
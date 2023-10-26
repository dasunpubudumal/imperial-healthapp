package org.health.imperialhealthapp.services;

import org.health.imperialhealthapp.models.GeneralResult;
import org.health.imperialhealthapp.models.domain.MeasurementType;
import org.health.imperialhealthapp.models.domain.Observation;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.health.imperialhealthapp.repositories.ObservationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ObservationServiceTest {

    ObservationRepository repository = mock(
            ObservationRepository.class
    );

    ObservationService service;

    @BeforeEach
    void setUp() {
        when(repository.findAll(any()))
                .thenReturn(new SliceImpl<>(
                        List.of(
                                Observation.builder()
                                        .measurementType(MeasurementType.builder().measurementType("x").unit("y").build())
                                        .date(Date.valueOf("2022-01-03"))
                                        .patient(1)
                                        .value(10.2)
                                        .build()
                        )
                ));
        service = new ObservationService(repository);
    }

    @Test
    @DisplayName("Check listing all observations")
    void listAll() {
        ResponseEntity<GeneralResult<Slice<ObservationDto>>> generalResultResponseEntity = service.listAll(
                PageRequest.of(0, 1)
        );
        assertTrue(Objects.nonNull(generalResultResponseEntity.getBody()));
        assertEquals(generalResultResponseEntity.getBody().getData().getContent().size(), 1);
    }
}
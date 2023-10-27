package org.health.imperialhealthapp.controllers;

import jakarta.transaction.Transactional;
import org.health.imperialhealthapp.IntegrationTest;
import org.health.imperialhealthapp.models.GeneralResult;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class ObservationControllerTest extends IntegrationTest {

    @Autowired
    private ObservationController controller;

    @DisplayName("Check context load.")
    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    @DisplayName("Check if listAll() method works")
    void listAll() {
        ResponseEntity<GeneralResult<Slice<ObservationDto>>> generalResultResponseEntity
                = controller.listAll(PageRequest.of(0, 1));
        assertNotNull(generalResultResponseEntity.getBody());
        assertNotNull(
                generalResultResponseEntity.getBody().getData()
        );
        assertEquals(
                1,
                generalResultResponseEntity.getBody().getData().getContent().size()
        );
    }

    @Test
    @DisplayName("Check if getById() method workds")
    void listOne() {
        String uuid = "a94d682f-e537-4d87-829f-c6d2af2ca0fc";
        ResponseEntity<GeneralResult<ObservationDto>> byId = controller.getById(uuid);
        assertNotNull(byId.getBody());
        assertNotNull(byId.getBody().getData());
        assertEquals(
                uuid,
                byId.getBody().getData().getId()
        );
        assertEquals(
                "skin-temperature",
                byId.getBody().getData().getMeasurementType()
        );
    }

    @Test
    @DisplayName("Check if save method works")
    void save() {
        ObservationDto observationDto = ObservationDto.builder()
                .measurementType("heart-rate")
                .unit("beats/minute")
                .date("2023-09-10T08:54:33Z")
                .value(76.0d)
                .patient(101)
                .build();
        try {
            controller.save(observationDto);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
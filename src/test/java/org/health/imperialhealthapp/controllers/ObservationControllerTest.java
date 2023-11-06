package org.health.imperialhealthapp.controllers;

import jakarta.transaction.Transactional;
import org.health.imperialhealthapp.IntegrationTest;
import org.health.imperialhealthapp.config.GeneralResult;
import org.health.imperialhealthapp.models.domain.Observation;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ObservationControllerTest extends IntegrationTest {

    @Autowired
    private ObservationController controller;

    final String uuid = UUID.randomUUID().toString();
    ObservationDto observationDto = ObservationDto.builder()
            .id(uuid)
            .measurementType("heart-rate")
            .unit("beats/minute")
            .date("2023-09-10T08:54:33Z")
            .value(76.0d)
            .patient(101)
            .build();

    @DisplayName("Check context load.")
    @Test
    @Order(1)
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    @DisplayName("Check if listAll() method works")
    @Order(2)
    @Transactional
    @WithMockUser(roles = "USER")
    void listAll() {
        ResponseEntity<GeneralResult<Page<ObservationDto>>> generalResultResponseEntity
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
    @DisplayName("Check if getById() method works")
    @Order(3)
    @Transactional
    @WithMockUser(roles = "USER")
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
    @Order(4)
    @Transactional
    @WithMockUser(roles = "USER")
    void save() {
        try {
            controller.save(observationDto);
            ResponseEntity<GeneralResult<ObservationDto>> byId = controller.getById(uuid);
            assertNotNull(byId.getBody());
            assertNotNull(byId.getBody().getData());
            assertEquals(
                    uuid,
                    byId.getBody().getData().getId()
            );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Check if update method works")
    @Order(5)
    @Transactional
    @WithMockUser(roles = "USER")
    void update() {
        try {
            controller.save(observationDto);
            observationDto.setPatient(103);
            controller.update(observationDto, uuid);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Check if delete method works")
    @Order(6)
    @Transactional
    @WithMockUser(roles = {"USER", "ADMIN"})
    void delete() {
        try {
            controller.save(observationDto);
            controller.delete(uuid);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
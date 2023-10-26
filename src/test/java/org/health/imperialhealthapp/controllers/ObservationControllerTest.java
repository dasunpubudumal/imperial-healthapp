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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

}
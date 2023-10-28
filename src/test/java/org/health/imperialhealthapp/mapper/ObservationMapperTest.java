package org.health.imperialhealthapp.mapper;

import org.health.imperialhealthapp.mapper.ObservationMapper;
import org.health.imperialhealthapp.models.domain.MeasurementType;
import org.health.imperialhealthapp.models.domain.Observation;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.health.imperialhealthapp.util.DateMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestConfiguration
@ComponentScan(basePackageClasses = {ObservationMapper.class})
class ObservationMapperTest {

    @Test
    @DisplayName("Check proper conversion of the dto mapper")
    void convertToDto() {
        Observation observation = Observation.builder()
                .id(UUID.randomUUID())
                .measurementType(MeasurementType.builder().measurementType("x").unit("y").build())
                .date(DateMapper.asDate("2023-09-05T15:12:23Z"))
                .patient(1)
                .value(10.2)
                .build();
        ObservationDto observationDto = ObservationMapper.INSTANCE.convertToDto(
                observation
        );
        assertEquals(
                observationDto.getMeasurementType(),
                "x"
        );
        assertEquals(
                observationDto.getUnit(),
                "y"
        );
        assertEquals(
                observationDto.getPatient(),
                1
        );
        assertEquals(
                observationDto.getValue(),
                10.2
        );
    }

}
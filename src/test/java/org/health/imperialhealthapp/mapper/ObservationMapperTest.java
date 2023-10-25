package org.health.imperialhealthapp.mapper;

import org.health.imperialhealthapp.mapper.ObservationMapper;
import org.health.imperialhealthapp.models.domain.MeasurementType;
import org.health.imperialhealthapp.models.domain.Observation;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestConfiguration
@ComponentScan(basePackageClasses = {ObservationMapper.class})
class ObservationMapperTest {

    @Test
    @DisplayName("Check proper conversion of the mapper")
    void convert() {

        ObservationDto dto = new ObservationDto(
                "2020-01-01",
                10,
                10.2,
                "heart-rate",
                "beats/minute"
        );

        Observation observation = ObservationMapper.INSTANCE.convert(
                dto
        );
        assertTrue(Objects.nonNull(observation));
        assertEquals(
                new SimpleDateFormat( "yyyy-MM-dd" )
                        .format( observation.getDate() ),
                "2020-01-01"
        );
        assertTrue(Objects.nonNull(observation.getMeasurementType()));
    }

    @Test
    @DisplayName("Check proper conversion of the dto mapper")
    void convertToDto() {
        Observation observation = Observation.builder()
                .measurementType(MeasurementType.builder().measurementType("x").unit("y").build())
                .date(Date.valueOf("2022-01-03"))
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
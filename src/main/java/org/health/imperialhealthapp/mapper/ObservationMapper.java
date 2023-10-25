package org.health.imperialhealthapp.mapper;

import jdk.jfr.Name;
import org.health.imperialhealthapp.models.domain.MeasurementType;
import org.health.imperialhealthapp.models.domain.Observation;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.health.imperialhealthapp.util.DateMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.sql.Date;

@Mapper(componentModel = "spring")
public interface ObservationMapper {

    ObservationMapper INSTANCE = Mappers.getMapper( ObservationMapper.class );

    @Mapping(target = "measurementType", ignore = true)
    @Mapping(source = "date", target = "date", qualifiedByName = "dateMap")
    @BeanMapping(builder = @Builder(disableBuilder = true))
    Observation convert(ObservationDto observationDto);

    @Mapping(target = "measurementType", source = "measurementType.measurementType")
    @Mapping(target = "unit", source = "measurementType.unit")
    @Mapping(source = "date", target = "date", qualifiedByName = "dateMapDto")
    @BeanMapping(builder = @Builder(disableBuilder = true))
    ObservationDto convertToDto(Observation observation);

    @Named(value = "dateMapDto")
    default String dateMapDto(Date date) {
        return DateMapper.asString(date);
    }

    @Named(value = "dateMap")
    default Date dateMap(String date) {
        return DateMapper.asDate(date);
    }

    @AfterMapping
    default void measurementTypeConvert(@MappingTarget Observation observation, ObservationDto dto) {
        observation.setMeasurementType(
                MeasurementType.builder()
                        .unit(dto.getUnit())
                        .measurementType(dto.getMeasurementType())
                        .build()
        );
    }

}

package org.health.imperialhealthapp.mapper;

import org.apache.commons.lang3.StringUtils;
import org.health.imperialhealthapp.models.domain.MeasurementType;
import org.health.imperialhealthapp.models.domain.Observation;
import org.health.imperialhealthapp.models.domain.Patient;
import org.health.imperialhealthapp.models.dto.ObservationDto;
import org.health.imperialhealthapp.util.DateMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ObservationMapper {

    ObservationMapper INSTANCE = Mappers.getMapper( ObservationMapper.class );

    @Mapping(target = "measurementType", ignore = true)
    @Mapping(source = "date", target = "date", qualifiedByName = "dateMap")
    @Mapping(source = "id", target = "id", qualifiedByName = "mapIdDomain")
    @Mapping(source = "patient", target = "patient", qualifiedByName = "convertPatientDao")
    @BeanMapping(builder = @Builder(disableBuilder = true))
    Observation convert(ObservationDto observationDto);

    @Mapping(target = "measurementType", source = "measurementType.measurementType")
    @Mapping(target = "unit", source = "measurementType.unit")
    @Mapping(source = "date", target = "date", qualifiedByName = "dateMapDto")
    @Mapping(source = "id", target = "id", qualifiedByName = "mapIdDto")
    @Mapping(source = "patient", target = "patient", qualifiedByName = "convertPatientDto")
    @BeanMapping(builder = @Builder(disableBuilder = true))
    ObservationDto convertToDto(Observation observation);

    @Named("convertPatientDao")
    default Patient convertPatient(Integer patientId) {
        return Objects.nonNull(patientId) ? Patient.builder().id(patientId).build() : null;
    }

    @Named("convertPatientDto")
    default Integer convertPatientId(Patient patient) {
        return Objects.nonNull(patient) && Objects.nonNull(patient.getId()) ? patient.getId() : null;
    }

    @Named("mapIdDomain")
    default UUID mapIdDomain(String id) {
        if (!StringUtils.isEmpty(id)) {
            return UUID.fromString(id);
        }
        return null;
    }

    @Named("mapIdDto")
    default String mapIdDto(UUID id) {
        return id.toString();
    }

    @Named(value = "dateMapDto")
    default String dateMapDto(OffsetDateTime date) {
        return DateMapper.asString(date);
    }

    @Named(value = "dateMap")
    default OffsetDateTime dateMap(String date) {
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

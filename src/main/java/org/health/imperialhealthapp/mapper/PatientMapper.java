package org.health.imperialhealthapp.mapper;

import org.health.imperialhealthapp.models.domain.Patient;
import org.health.imperialhealthapp.models.dto.PatientDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    @BeanMapping(builder = @Builder(disableBuilder = true))
    PatientDto convert(Patient patient);

    @BeanMapping(builder = @Builder(disableBuilder = true))
    Patient convertToDao(PatientDto patientDto);

}

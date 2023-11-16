package org.health.imperialhealthapp.services;

import org.health.imperialhealthapp.config.GeneralResult;
import org.health.imperialhealthapp.exceptions.InternalServerException;
import org.health.imperialhealthapp.exceptions.InvalidRequestException;
import org.health.imperialhealthapp.mapper.PatientMapper;
import org.health.imperialhealthapp.models.Status;
import org.health.imperialhealthapp.models.domain.Patient;
import org.health.imperialhealthapp.models.dto.PatientDto;
import org.health.imperialhealthapp.repositories.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ResponseEntity<GeneralResult<PatientDto>> findPatient(Integer id) {
        if (Objects.isNull(id)) throw new InvalidRequestException("Invalid ID");
        return ResponseEntity.ok(
                GeneralResult.<PatientDto>builder().status(Status.SUCCESS).data(
                        PatientMapper.INSTANCE.convert(
                                repository
                                        .findById(id)
                                        .orElseThrow(() -> new InvalidRequestException("Patient not found for the given id."))
                        )
                ).build()
        );
    }

    @Transactional
    public ResponseEntity<GeneralResult<Void>> savePatient(@RequestBody PatientDto patientDto) {
        try {
            Patient patient = PatientMapper.INSTANCE.convertToDao(patientDto);
            repository.save(patient);
            return ResponseEntity.ok(
                    GeneralResult.<Void>builder().status(Status.SUCCESS).build()
            );
        } catch (Exception e) {
            throw new InternalServerException("Server error");
        }
    }

    @Transactional
    public ResponseEntity<GeneralResult<Void>> deletePatient(Integer id) {
        if (Objects.isNull(id)) throw new InvalidRequestException("Invalid ID");
        try {
            repository.delete(
                    Patient.builder().id(id).build()
            );
            return ResponseEntity.ok(
                    GeneralResult.<Void>builder().status(Status.SUCCESS).build()
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Patient with given ID does not exist!");
        } catch (Exception e) {
            throw new InternalServerException("Server error");
        }
    }

    @Transactional
    public ResponseEntity<GeneralResult<Void>> updatePatient(PatientDto patientDto, String id) {
        if (Objects.isNull(id)) throw new InvalidRequestException("Invalid ID");
        try {
            repository
                    .findById(Integer.parseInt(id))
                    .orElseThrow(() -> new InvalidRequestException("Patient with given ID does not exist!"));
            repository.save(PatientMapper.INSTANCE.convertToDao(
                    patientDto
            ));
            return ResponseEntity.ok(
                    GeneralResult.<Void>builder().status(Status.SUCCESS).build()
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Patient with given ID does not exist!");
        }
    }
}

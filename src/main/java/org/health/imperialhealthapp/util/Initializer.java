package org.health.imperialhealthapp.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.health.imperialhealthapp.models.domain.MeasurementType;
import org.health.imperialhealthapp.models.domain.Patient;
import org.health.imperialhealthapp.repositories.MeasurementTypeRepository;
import org.health.imperialhealthapp.repositories.ObservationRepository;
import org.health.imperialhealthapp.repositories.PatientRepository;

@UtilityClass
@Slf4j
public class Initializer {

    public static void initializeObservations(ObservationRepository repository) {
        repository.saveAll(FakeObservationGenerator.generateObservations());
    }

    public static void intializePatients(PatientRepository patientRepository) {
        patientRepository.findById(101).ifPresentOrElse(
                (record) -> log.info("Patient already found"),
                () -> patientRepository.save(Patient.builder().id(101).firstName("Patient").lastName("One").build()));
        patientRepository.findById(102).ifPresentOrElse(
                (record) -> log.info("Patient already found"),
                () -> patientRepository.save(Patient.builder().id(102).firstName("Patient").lastName("Two").build()));
        patientRepository.findById(103).ifPresentOrElse(
                (record) -> log.info("Patient already found"),
                () -> patientRepository.save(Patient.builder().id(103).firstName("Patient").lastName("Three").build()));
    }

    public static void initializeMeasurementTypes(MeasurementTypeRepository measurementTypeRepository) {
        measurementTypeRepository.findByMeasurementType("heart-rate").ifPresentOrElse(
                (record) -> log.info("Record already exists for heart-rate"),
                () -> measurementTypeRepository.save(
                        MeasurementType.builder()
                                .measurementType("heart-rate")
                                .unit("beats/minute")
                                .build()
                )
        );
        measurementTypeRepository.findByMeasurementType("skin-temperature").ifPresentOrElse(
                (record) -> log.info("Record already exists for skin-temperature"),
                () -> measurementTypeRepository.save(
                        MeasurementType.builder()
                                .measurementType("skin-temperature")
                                .unit("degrees Celcius")
                                .build()
                )
        );
        measurementTypeRepository.findByMeasurementType("respiratory-rate").ifPresentOrElse(
                (record) -> log.info("Record already exists for respiratory-rate"),
                () -> measurementTypeRepository.save(
                        MeasurementType.builder()
                                .measurementType("respiratory-rate")
                                .unit("breaths/minute")
                                .build()
                )
        );
    }

}

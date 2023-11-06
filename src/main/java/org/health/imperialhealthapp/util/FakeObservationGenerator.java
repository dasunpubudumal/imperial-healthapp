package org.health.imperialhealthapp.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.health.imperialhealthapp.models.domain.MeasurementType;
import org.health.imperialhealthapp.models.domain.Observation;
import org.health.imperialhealthapp.models.domain.Patient;

import java.util.List;

@UtilityClass
@Slf4j
public class FakeObservationGenerator {

    private static String HEART_RATE = "heart-rate";
    private static String SKIN_TEMP = "skin-temperature";
    private static String RESPIRATORY_RATE = "respiratory-rate";


    public static List<Observation> generateObservations() {
        log.info("Generating observations...");
        return List.of(
                Observation.builder()
                        .value(65d)
                        .patient(Patient.builder().id(101).build())
                        .measurementType(MeasurementType.builder().measurementType(HEART_RATE).build())
                        .date(DateMapper.asDate("2023-09-06T11:02:44Z"))
                        .build(),
                Observation.builder()
                        .value(37.2)
                        .patient(Patient.builder().id(101).build())
                        .measurementType(MeasurementType.builder().measurementType(SKIN_TEMP).build())
                        .date(DateMapper.asDate("2023-09-07T11:23:24Z"))
                        .build(),
                Observation.builder()
                        .value(15d)
                        .patient(Patient.builder().id(101).build())
                        .measurementType(MeasurementType.builder().measurementType(RESPIRATORY_RATE).build())
                        .date(DateMapper.asDate("2023-09-06T11:02:44Z"))
                        .build(),
                Observation.builder()
                        .value(76d)
                        .patient(Patient.builder().id(102).build())
                        .measurementType(MeasurementType.builder().measurementType(HEART_RATE).build())
                        .date(DateMapper.asDate("2023-09-04T08:54:33Z"))
                        .build(),
                Observation.builder()
                        .value(18d)
                        .patient(Patient.builder().id(102).build())
                        .measurementType(MeasurementType.builder().measurementType(RESPIRATORY_RATE).build())
                        .date(DateMapper.asDate("2023-09-04T08:54:33Z"))
                        .build(),
                Observation.builder()
                        .value(37.8)
                        .patient(Patient.builder().id(103).build())
                        .measurementType(MeasurementType.builder().measurementType(SKIN_TEMP).build())
                        .date(DateMapper.asDate("2023-09-05T15:12:23Z"))
                        .build()
        );
    }

}

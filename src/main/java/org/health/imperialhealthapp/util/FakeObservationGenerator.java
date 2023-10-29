package org.health.imperialhealthapp.util;

import lombok.experimental.UtilityClass;
import org.health.imperialhealthapp.models.domain.MeasurementType;
import org.health.imperialhealthapp.models.domain.Observation;

import java.util.List;

@UtilityClass
public class FakeObservationGenerator {

    private static String HEART_RATE = "heart-rate";
    private static String SKIN_TEMP = "skin-temperature";
    private static String RESPIRATORY_RATE = "respiratory-rate";


    public static List<Observation> generateObservations() {
        return List.of(
                Observation.builder()
                        .value(65d)
                        .patient(101)
                        .measurementType(MeasurementType.builder().measurementType(HEART_RATE).build())
                        .date(DateMapper.asDate("2023-09-06T11:02:44Z"))
                        .build(),
                Observation.builder()
                        .value(37.2)
                        .patient(101)
                        .measurementType(MeasurementType.builder().measurementType(SKIN_TEMP).build())
                        .date(DateMapper.asDate("2023-09-07T11:23:24Z"))
                        .build(),
                Observation.builder()
                        .value(15d)
                        .patient(101)
                        .measurementType(MeasurementType.builder().measurementType(RESPIRATORY_RATE).build())
                        .date(DateMapper.asDate("2023-09-06T11:02:44Z"))
                        .build(),
                Observation.builder()
                        .value(76d)
                        .patient(102)
                        .measurementType(MeasurementType.builder().measurementType(HEART_RATE).build())
                        .date(DateMapper.asDate("2023-09-04T08:54:33Z"))
                        .build(),
                Observation.builder()
                        .value(18d)
                        .patient(102)
                        .measurementType(MeasurementType.builder().measurementType(RESPIRATORY_RATE).build())
                        .date(DateMapper.asDate("2023-09-04T08:54:33Z"))
                        .build(),
                Observation.builder()
                        .value(37.8)
                        .patient(103)
                        .measurementType(MeasurementType.builder().measurementType(SKIN_TEMP).build())
                        .date(DateMapper.asDate("2023-09-05T15:12:23Z"))
                        .build()
        );
    }

}

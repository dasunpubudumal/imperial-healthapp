package org.health.imperialhealthapp.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObservationDto {

    private String id;
    private String date;
    private Integer patient;
    private Double value;
    private String measurementType;
    private String unit;

}

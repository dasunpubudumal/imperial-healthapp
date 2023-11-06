package org.health.imperialhealthapp.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDto {

    private String id;
    private String firstName;
    private String lastName;

}

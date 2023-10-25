package org.health.imperialhealthapp.models.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "measurement_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementType {
    @Id
    @Column(name = "measurement_type")
    private String measurementType;
    @Column(name = "unit")
    private String unit;
}

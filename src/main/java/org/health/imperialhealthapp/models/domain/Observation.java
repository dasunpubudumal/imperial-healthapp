package org.health.imperialhealthapp.models.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "observations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Observation {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "date")
    private OffsetDateTime date;
    @Column(name = "patient")
    private Integer patient;
    @Column(name = "value")
    private Double value;
    @ManyToOne
    @JoinColumn(name = "measurement_type", updatable = false)
    private MeasurementType measurementType;

    @PrePersist
    private void uuid() {
        if (Objects.isNull(this.id)) {
            this.id = UUID.randomUUID();
        }
    }
}

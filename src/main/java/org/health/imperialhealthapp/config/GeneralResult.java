package org.health.imperialhealthapp.config;

import lombok.*;
import org.health.imperialhealthapp.models.Status;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralResult<T> {

    private T data;
    private Status status;

}

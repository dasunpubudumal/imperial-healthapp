package org.health.imperialhealthapp.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralResult<T> {

    private T data;
    private Status status;

}

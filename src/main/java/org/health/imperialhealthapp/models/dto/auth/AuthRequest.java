package org.health.imperialhealthapp.models.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {

    private String username;
    private String password;

}

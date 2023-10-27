package org.health.imperialhealthapp.models.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;

}

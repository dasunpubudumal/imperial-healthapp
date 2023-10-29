package org.health.imperialhealthapp.services;

import lombok.extern.slf4j.Slf4j;
import org.health.imperialhealthapp.config.GeneralResult;
import org.health.imperialhealthapp.exceptions.InternalServerException;
import org.health.imperialhealthapp.exceptions.InvalidRequestException;
import org.health.imperialhealthapp.models.Status;
import org.health.imperialhealthapp.models.domain.Role;
import org.health.imperialhealthapp.models.domain.User;
import org.health.imperialhealthapp.models.dto.auth.AuthRequest;
import org.health.imperialhealthapp.models.dto.auth.AuthResponse;
import org.health.imperialhealthapp.models.dto.auth.RegisterRequest;
import org.health.imperialhealthapp.repositories.RoleRepository;
import org.health.imperialhealthapp.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder encoder,
                                 RoleRepository roleRepository,
                                 JwtEncoder jwtEncoder,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public ResponseEntity<GeneralResult<AuthResponse>> login(AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
            String token = obtainToken(authenticate).orElseThrow();
            return ResponseEntity.ok(
                    GeneralResult.<AuthResponse>builder().data(
                            AuthResponse.builder()
                                    .token(token)
                                    .username(authRequest.getUsername())
                                    .build()
                    ).status(Status.SUCCESS).build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InvalidRequestException("Invalid credentials.");
        }
    }

    @Transactional
    public ResponseEntity<GeneralResult<Void>> register(RegisterRequest request) {
        try {
            String role = request.getRole();
            Role roleExists = roleRepository.findByRoleName(role).orElseThrow(() -> new InvalidRequestException(
                    "Role not found"
            ));
            User user = User.builder()
                    .roles(Set.of(roleExists))
                    .username(request.getUsername())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .password(encoder.encode(request.getPassword()))
                    .build();
            User save = userRepository.save(user);
            System.out.println(save);
            return ResponseEntity.ok(
                    GeneralResult.<Void>builder().status(Status.SUCCESS).build()
            );
        } catch (Exception e) {
            throw new InternalServerException();
        }
    }

    private Optional<String> obtainToken(Authentication authentication) {
           try {
               Instant now = Instant.now();
               String roles = authentication
                       .getAuthorities()
                       .stream()
                       .map(GrantedAuthority::getAuthority)
                       .collect(Collectors.joining(" "));
               JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                       .issuer("self")
                       .issuedAt(now)
                       .expiresAt(now.plus(15, ChronoUnit.MINUTES))
                       .subject(authentication.getName())
                       .claim("roles", roles)
                       .build();
               return Optional.of(
                       jwtEncoder.encode(JwtEncoderParameters.from(
                               claimsSet
                       )).getTokenValue()
               );
           } catch (Exception e) {
               log.error(e.getMessage());
           }
           return Optional.empty();
    }
}

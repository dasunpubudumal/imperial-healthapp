package org.health.imperialhealthapp.controllers;

import org.health.imperialhealthapp.config.Executor;
import org.health.imperialhealthapp.config.GeneralResult;
import org.health.imperialhealthapp.models.dto.auth.AuthRequest;
import org.health.imperialhealthapp.models.dto.auth.AuthResponse;
import org.health.imperialhealthapp.models.dto.auth.RegisterRequest;
import org.health.imperialhealthapp.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final Executor executor;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.executor = new Executor();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<GeneralResult<Void>> register(@RequestBody RegisterRequest request) {
        return executor.execute(() -> authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResult<AuthResponse>> login(@RequestBody AuthRequest request) {
        return executor.execute(() -> authenticationService.login(request));
    }

}

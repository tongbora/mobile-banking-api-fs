package org.istad.mobilebankingfs.controller;
import lombok.RequiredArgsConstructor;
import org.istad.mobilebankingfs.dto.auth.RegisterRequest;
import org.istad.mobilebankingfs.dto.auth.RegisterResponse;
import org.istad.mobilebankingfs.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }
}

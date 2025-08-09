package org.istad.mobilebankingfs.service.impl;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.istad.mobilebankingfs.dto.auth.RegisterRequest;
import org.istad.mobilebankingfs.dto.auth.RegisterResponse;
import org.istad.mobilebankingfs.service.AuthService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final Keycloak keycloak;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        if (!registerRequest.password().equals(registerRequest.confirmedPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        UserRepresentation user = new UserRepresentation();
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(registerRequest.password());
        user.setCredentials(List.of(credential));

        user.setEmailVerified(false);
        user.setEnabled(true);

        try (Response response = keycloak.realm("mbapi")
                .users()
                .create(user)) {
            if (response.getStatus() == HttpStatus.CREATED.value()) {

                return RegisterResponse.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build();
            }
            throw new ResponseStatusException(HttpStatus.valueOf(response.getStatus()),"Failed to create user");
        }
    }


}
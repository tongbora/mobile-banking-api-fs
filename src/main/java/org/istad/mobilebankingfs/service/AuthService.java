package org.istad.mobilebankingfs.service;


import org.istad.mobilebankingfs.dto.auth.RegisterRequest;
import org.istad.mobilebankingfs.dto.auth.RegisterResponse;

public interface AuthService {

        RegisterResponse register(RegisterRequest resisterRequest);

}

package com.easygoing.backend.services.authentication.service

import com.easygoing.backend.services.authentication.dto.AuthenticationRequest
import com.easygoing.backend.services.authentication.dto.AuthenticationResponse
import com.easygoing.backend.services.authentication.dto.RegisterRequest
import com.easygoing.backend.services.authentication.dto.RegisterResponse
import org.springframework.http.ResponseEntity

interface AuthenticationService {
    fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse?
    fun register(registerRequest: RegisterRequest): ResponseEntity<RegisterResponse>
}
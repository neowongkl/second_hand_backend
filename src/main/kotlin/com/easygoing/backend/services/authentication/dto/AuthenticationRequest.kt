package com.easygoing.backend.services.authentication.dto

import javax.validation.constraints.NotBlank

data class AuthenticationRequest (
        @field:NotBlank(message = "username should not be null")
        val username: String,
        @field:NotBlank(message = "password should not be null")
        val password: String,
)
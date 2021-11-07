package com.easygoing.backend.services.authentication.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class RegisterRequest (
        @field:NotBlank(message = "username should not be null")
        @field:Size(min=1, max = 50, message = "email must be between {min} and {max} characters long")
        val username: String,
        @field:Email
        val email: String,
        @field:Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~\$^+=<>]).{8,20}\$", message = "Invalid password")
        val password: String,
)
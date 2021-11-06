package com.easygoing.backend.services.authentication.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class RegisterRequest (
        @field:NotBlank(message = "username should not be null")
        val username: String,
        @field:Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~\$^+=<>]).{8,20}\$", message = "invalid password")
        val password: String,
)
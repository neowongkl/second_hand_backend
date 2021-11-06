package com.easygoing.backend.services.authentication.dto

import javax.validation.constraints.NotBlank

data class RegisterRequest (
        @field:NotBlank(message = "username should not be null")
        val username: String,
        val password: String,
)
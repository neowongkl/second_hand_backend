package com.easygoing.backend.services.authentication.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class AuthenticationRequest (
        @field:NotBlank(message = "username/email should not be null")
        @field:Size(max = 255, message = "username/email must less than {max} character")
        val usernameOrEmail: String,
        @field:NotBlank(message = "password should not be null")
        @field:Size(max = 50, message = "password must less than {max} character")
        val password: String,
)
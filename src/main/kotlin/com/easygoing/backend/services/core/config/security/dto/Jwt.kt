package com.easygoing.backend.services.core.config.security.dto

data class Jwt(
    val enable: Boolean,
    val secretKey: String,
    val expiryInMinutes: Long,
)
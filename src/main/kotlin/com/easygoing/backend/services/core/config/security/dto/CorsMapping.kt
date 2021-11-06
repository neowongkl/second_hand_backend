package com.easygoing.backend.services.core.config.security.dto

data class CorsMapping (
    val path : String,
    val allowedOrigins: List<String>,
    val allowedHeaders: List<String>,
    val allowedMethods: List<String>,
    val allowCredentials: Boolean,
    val exposedHeaders: List<String>
)
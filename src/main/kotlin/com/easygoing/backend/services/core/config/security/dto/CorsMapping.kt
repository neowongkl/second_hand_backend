package com.easygoing.backend.services.core.config.security.dto

data class CorsMapping (
    var path : String = "",
    var allowedOrigins: List<String> = emptyList(),
    var allowedHeaders: List<String> = emptyList(),
    var allowedMethods: List<String> = emptyList(),
    var allowCredentials: Boolean = false,
    var exposedHeaders: List<String> = emptyList()
)
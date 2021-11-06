package com.easygoing.backend.services.core.config.security.dto

data class WebSecurity (
    val corsMappings : List<CorsMapping>,
    val jwt : Jwt
)
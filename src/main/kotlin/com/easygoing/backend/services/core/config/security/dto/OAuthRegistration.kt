package com.easygoing.backend.services.core.config.security.dto

data class OAuthRegistration(
        var registrationId: String = "",
        var clientId: String = "",
        var clientSecret: String = "",
        var redirectUri: String = "",
        var scope: List<String> = emptyList()
)
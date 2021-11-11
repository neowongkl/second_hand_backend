package com.easygoing.backend.services.core.config.security.dto

data class OAuth (
        val registration: List<Registration>,
        val authorizedUrlList: List<String>
)

data class Registration(
        val registrationId: String,
        val clientId: String,
        val clientSecret: String,
        val redirectUri: String,
        val scope: List<String>
)
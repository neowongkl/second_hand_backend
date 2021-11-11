package com.easygoing.backend.services.core.config.security.constant

enum class CookieKey(val key: String) {
    OAUTH2_AUTHORIZATION_REQUEST("oauth2_auth_request"),
    REDIRECT_URI_PARAM("redirect_uri")
}
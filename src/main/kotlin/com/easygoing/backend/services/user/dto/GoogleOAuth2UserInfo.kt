package com.easygoing.backend.services.user.dto

import com.easygoing.backend.services.user.constant.AuthProvider

class GoogleOAuth2UserInfo: OAuth2UserInfo {
    constructor(attributes: Map<String,Any>?) : super(attributes) {
        this.id = attributes?.get("sub")?.toString()
        this.name = attributes?.get("name")?.toString()
        this.email = attributes?.get("email")?.toString()
        this.authProvider = AuthProvider.GOOGLE
    }
}
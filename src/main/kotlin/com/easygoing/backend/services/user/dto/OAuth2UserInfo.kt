package com.easygoing.backend.services.user.dto

import com.easygoing.backend.services.user.constant.AuthProvider

abstract class OAuth2UserInfo {
    var attributes : Map<String, Any>?
    var id: String? = null
    var name: String? = null
    var email: String? = null
    var authProvider: AuthProvider? = null

    constructor(attributes: Map<String, Any>?){
        this.attributes = attributes
    }
}
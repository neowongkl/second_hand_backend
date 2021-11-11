package com.easygoing.backend.services.user.factory

import com.easygoing.backend.services.user.constant.AuthProvider
import com.easygoing.backend.services.user.dto.GithubOAuth2UserInfo
import com.easygoing.backend.services.user.dto.OAuth2UserInfo

class OAuth2UserInfoFactory {


    companion object{
        fun getOAuth2UserInfo(registrationId: String, attributes: Map<String, Any>?):
                OAuth2UserInfo {
            return if (registrationId.equals(AuthProvider.GITHUB.toString(), ignoreCase = true)) {
                GithubOAuth2UserInfo(attributes)
//        } else if (registrationId.equals(AuthProvider.FACEBOOK.toString(), ignoreCase = true)) {
//            FacebookOAuth2UserInfo(attributes)
//        } else if (registrationId.equals(AuthProvider.GOOGLE.toString(), ignoreCase = true)) {
//            GoogleOAuth2UserInfo(attributes)
            } else {
                throw Exception("$registrationId is not supported yet.")
            }
        }
    }

}
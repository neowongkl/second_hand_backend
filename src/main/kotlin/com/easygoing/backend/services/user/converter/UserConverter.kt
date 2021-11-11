package com.easygoing.backend.services.user.converter

import com.easygoing.backend.services.user.dao.AuthorityDao
import com.easygoing.backend.services.user.dao.UserDao
import com.easygoing.backend.services.user.dto.CustomUserDetails
import com.easygoing.backend.services.user.dto.OAuth2UserInfo
import org.springframework.stereotype.Component

@Component
class UserConverter {
    fun userDaoToCustomUserDetail(userDao: UserDao): CustomUserDetails{
        return CustomUserDetails(
            userId = userDao.id,
            username = userDao.username,
            password = userDao.password,
            authorities = userDao.authorities.map { it.authority },
            enable = userDao.enable,
            email = userDao.email,
            oAuthAttribute = mutableMapOf()
        )
    }

    fun oAuth2UserInfoToUserDao(oAuth2UserInfo: OAuth2UserInfo, roles: List<AuthorityDao>): UserDao{
        return UserDao(
            username = oAuth2UserInfo.name.toString(),
            providerId = oAuth2UserInfo.id.toString(),
            authProvider = oAuth2UserInfo.authProvider!!,
            email = oAuth2UserInfo.email.toString(),
            enable = true,
            password = "N/A"
        ).apply {
            this.authorities.addAll(roles.onEach { it.user = this })
        }
    }

}
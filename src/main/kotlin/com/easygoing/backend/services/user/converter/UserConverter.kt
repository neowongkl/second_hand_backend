package com.easygoing.backend.services.user.converter

import com.easygoing.backend.services.user.dao.UserDao
import com.easygoing.backend.services.user.dto.CustomUserDetails
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
        )
    }

}
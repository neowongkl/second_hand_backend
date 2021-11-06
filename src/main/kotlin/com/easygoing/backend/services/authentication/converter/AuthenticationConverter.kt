package com.easygoing.backend.services.authentication.converter

import com.easygoing.backend.services.authentication.dto.RegisterRequest
import com.easygoing.backend.services.user.constant.Role
import com.easygoing.backend.services.user.dao.AuthorityDao
import com.easygoing.backend.services.user.dao.UserDao
import org.springframework.stereotype.Component

@Component
class AuthenticationConverter {

    fun registerRequestToUserDao(registerRequest: RegisterRequest): UserDao{
        return UserDao(
            username = registerRequest.username,
            password = registerRequest.password,
            enable = true,
        ).apply {
            this.authorities.add(AuthorityDao(authority = Role.USER.role, user = this))
        }
    }

}
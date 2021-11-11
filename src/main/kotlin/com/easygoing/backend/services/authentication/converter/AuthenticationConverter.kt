package com.easygoing.backend.services.authentication.converter

import com.easygoing.backend.services.authentication.dto.RegisterRequest
import com.easygoing.backend.services.user.constant.AuthProvider
import com.easygoing.backend.services.user.constant.RoleType
import com.easygoing.backend.services.user.dao.AuthorityDao
import com.easygoing.backend.services.user.dao.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthenticationConverter {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    //TODO change role assign method
    fun registerRequestToUserDao(registerRequest: RegisterRequest): UserDao{
        return UserDao(
            username = registerRequest.username,
            password = passwordEncoder.encode(registerRequest.password),
            enable = true,
            email = registerRequest.email,
            authProvider = AuthProvider.LOCAL,
            providerId = "831"
        ).apply {
            this.authorities.add(AuthorityDao(authority = RoleType.USER.role, user = this))
        }
    }

}
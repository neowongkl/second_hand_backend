package com.easygoing.backend.services.user.service

import com.easygoing.backend.services.user.dao.UserDao
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

interface CustomUserDetailsService : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(usernameOrEmail: String?): UserDetails?

    fun isValidUserName(username: String): Boolean
    fun isValidEmail(email: String): Boolean

    fun createUser(userDao: UserDao)
}
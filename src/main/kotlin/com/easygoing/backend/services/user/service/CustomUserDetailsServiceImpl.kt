package com.easygoing.backend.services.user.service

import com.easygoing.backend.services.user.converter.UserConverter
import com.easygoing.backend.services.user.dao.UserDao
import com.easygoing.backend.services.user.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsServiceImpl: CustomUserDetailsService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userConverter: UserConverter

    override fun loadUserByUsername(usernameOrEmail: String?): UserDetails? {
        if (usernameOrEmail == null) {
            logger.error("username is null")
            return null
        }
        val userDao = userRepository.findByusernameOrEmail(usernameOrEmail, usernameOrEmail) ?: throw UsernameNotFoundException("username/email: $usernameOrEmail is not found")
        return userConverter.userDaoToCustomUserDetail(userDao)
    }

    override fun isValidUserName(username: String): Boolean{
        return takeIf { userRepository.existsByusername(username) }?.let {
            logger.error("username is already taken")
            false
        }?: true
    }

    override fun isValidEmail(email: String): Boolean{
        return takeIf { userRepository.existsByemail(email) }?.let {
            logger.error("email is already in use")
            false
        }?: true
    }

    override fun createUser(userDao: UserDao) {
        if (!isValidUserName(userDao.username)){
            return
        }
       userRepository.save(userDao)
        return
    }

}
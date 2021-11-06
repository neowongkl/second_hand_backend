package com.easygoing.backend.services.user.service

import com.easygoing.backend.services.user.converter.UserConverter
import com.easygoing.backend.services.user.dao.UserDao
import com.easygoing.backend.services.user.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomUserDetailsServiceImpl: CustomUserDetailsService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userConverter: UserConverter

    override fun loadUserByUsername(username: String?): UserDetails? {
        if (username == null) {
            logger.error("username is null")
            return null
        }
        val userDao = userRepository.findByusername(username) ?: throw UsernameNotFoundException("username: $username is not found")
        return userConverter.userDaoToCustomUserDetail(userDao)
    }

    override fun isValidUserName(username: String): Boolean{
        return userRepository.findByusername(username)?.let {
            logger.error("Invalid username, duplicate username")
            false
        }?: true
    }

    override fun createUser(userDao: UserDao): Boolean {
        if (!isValidUserName(userDao.username)){
            return false
        }
       userRepository.save(userDao)
        return true
    }

}
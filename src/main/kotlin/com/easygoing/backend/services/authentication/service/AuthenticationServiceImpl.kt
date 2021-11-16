package com.easygoing.backend.services.authentication.service

import com.easygoing.backend.services.authentication.converter.AuthenticationConverter
import com.easygoing.backend.services.authentication.dto.AuthenticationRequest
import com.easygoing.backend.services.authentication.dto.AuthenticationResponse
import com.easygoing.backend.services.authentication.dto.RegisterRequest
import com.easygoing.backend.services.authentication.dto.RegisterResponse
import com.easygoing.backend.services.core.config.security.util.JwtUtil
import com.easygoing.backend.services.user.constant.RoleType
import com.easygoing.backend.services.user.dao.AuthorityDao
import com.easygoing.backend.services.user.service.CustomUserDetailsService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl: AuthenticationService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var authenticationConverter: AuthenticationConverter

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsService

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    override fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse?{
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(authenticationRequest.usernameOrEmail, authenticationRequest.password)
            )
        }catch (e: Exception){
            logger.error(e.message)
            e.printStackTrace()
            throw Exception("Incorrect username/password")
        }

        return customUserDetailsService.loadUserByUsername(authenticationRequest.usernameOrEmail)?.let { _userDetail ->
            jwtUtil.generateToken(_userDetail)?.let { _jwt->
                AuthenticationResponse(_jwt)
            }
        }
    }

    override fun register(registerRequest: RegisterRequest): ResponseEntity<RegisterResponse>{
        if ( !customUserDetailsService.isValidUserName(registerRequest.username) ) {
            return ResponseEntity.badRequest().body(RegisterResponse(false, "username is already taken"))
        }
        if ( !customUserDetailsService.isValidEmail(registerRequest.email) ){
            return ResponseEntity.badRequest().body(RegisterResponse(false, "email is already in use"))
        }
        val authorities = listOf(AuthorityDao(authority = RoleType.USER.role))
        val newUserDao = authenticationConverter.registerRequestToUserDao(registerRequest, authorities)
        customUserDetailsService.createUser(newUserDao)
        return ResponseEntity.ok().body(RegisterResponse(true, "User registered successfully"))
    }
}
package com.easygoing.backend.services.authentication.controller

import com.easygoing.backend.services.authentication.dto.AuthenticationRequest
import com.easygoing.backend.services.authentication.dto.AuthenticationResponse
import com.easygoing.backend.services.authentication.dto.RegisterRequest
import com.easygoing.backend.services.authentication.service.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["authentication"])
class AuthenticationController {

    @Autowired
    private lateinit var authenticationService: AuthenticationService

    @RequestMapping(value = ["authenticate"], method = [RequestMethod.POST])
    fun authenticate(@RequestBody authenticationRequest: AuthenticationRequest): AuthenticationResponse?{
        return authenticationService.authenticate(authenticationRequest)
    }

    @RequestMapping(value = ["register"], method = [RequestMethod.POST])
    fun register(@RequestBody registerRequest: RegisterRequest): Boolean{
        return authenticationService.register(registerRequest)
    }

}
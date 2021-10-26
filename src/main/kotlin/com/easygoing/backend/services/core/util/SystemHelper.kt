package com.easygoing.backend.services.core.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class SystemHelper {

    @Autowired
    private lateinit var environment: Environment

    //Assume that only 1 active profiles
    fun getActiveProfile() : String {
        return  environment.activeProfiles.takeIf {
            it.isNotEmpty()
        }?.get(0) ?: environment.defaultProfiles[0]
    }

}
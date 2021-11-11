package com.easygoing.backend.services.core.config.security

import com.easygoing.backend.services.core.config.security.dto.OAuth
import com.easygoing.backend.services.core.config.security.dto.Registration
import com.easygoing.backend.services.core.config.security.util.SecurityUtil
import com.easygoing.backend.services.user.constant.AuthProvider
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import javax.annotation.PostConstruct

@Configuration
class OAuthConfiguration {

    @Autowired
    private lateinit var securityUtil: SecurityUtil

    private var oAuth: OAuth? = null

    @PostConstruct
    fun postConstruct(){
        oAuth = securityUtil.oAuth
    }

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val clientRegistrations = oAuth?.registration?.let {
            buildClientRegistration(it)
        }
        return InMemoryClientRegistrationRepository(clientRegistrations)
    }

    private fun buildClientRegistration(registrations: List<Registration>): List<ClientRegistration>{
        val clientRegistrations = mutableListOf<ClientRegistration>()
        registrations.forEach { _registration->
            if ( _registration.registrationId.equals(AuthProvider.GITHUB.toString(), ignoreCase = true)){
                clientRegistrations.add(
                    CommonOAuth2Provider.GITHUB
                        .getBuilder(AuthProvider.GITHUB.toString().lowercase())
                        .clientId(_registration.clientId)
                        .clientSecret(_registration.clientSecret)
                        .redirectUri(_registration.redirectUri)
                        .scope(_registration.redirectUri)
                        .build()
                )
            }
        }
        return clientRegistrations
    }


}
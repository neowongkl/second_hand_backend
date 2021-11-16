package com.easygoing.backend.services.core.config.security

import com.easygoing.backend.services.core.config.security.dto.OAuthRegistration
import com.easygoing.backend.services.user.constant.AuthProvider
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository

@Configuration
@ConfigurationProperties(prefix = "websecurity.oauth")
class OAuthConfiguration {

    var enable = true
    var registrations: List<OAuthRegistration>? = null
    lateinit var authorizedUrlList: List<String>

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val clientRegistrations = registrations?.let {
            buildClientRegistration(it)
        }
        return InMemoryClientRegistrationRepository(clientRegistrations)
    }

    private fun buildClientRegistration(registrations: List<OAuthRegistration>): List<ClientRegistration>{
        val clientRegistrations = mutableListOf<ClientRegistration>()
        registrations.forEach { _registration->
            if ( _registration.registrationId.equals(AuthProvider.GITHUB.toString(), ignoreCase = true)){
                clientRegistrations.add(
                    CommonOAuth2Provider.GITHUB
                        .getBuilder(AuthProvider.GITHUB.toString().lowercase())
                        .clientId(_registration.clientId)
                        .clientSecret(_registration.clientSecret)
                        .redirectUri(_registration.redirectUri)
                        .scope(_registration.scope)
                        .build()
                )
            }else if ( _registration.registrationId.equals(AuthProvider.GOOGLE.toString(), ignoreCase = true)){
                clientRegistrations.add(
                    CommonOAuth2Provider.GOOGLE
                        .getBuilder(AuthProvider.GOOGLE.toString().lowercase())
                        .clientId(_registration.clientId)
                        .clientSecret(_registration.clientSecret)
                        .redirectUri(_registration.redirectUri)
                        .scope(_registration.scope)
                        .build()
                )
            }
        }
        return clientRegistrations
    }

    fun isAuthorizedRedirectUri(redirectUri: String?): Boolean {
        return redirectUri != null && authorizedUrlList.contains(redirectUri)
    }


}
package com.easygoing.backend.services.core.config.security

import com.easygoing.backend.services.core.config.security.dto.CorsMapping
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ConfigurationProperties(prefix = "websecurity.cors")
class CorsConfiguration {

    var corsMappings : List<CorsMapping> = emptyList()

    @Bean
    fun corsMapping(): WebMvcConfigurer? {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                corsMappings.forEach { _corsMapping->
                    registry.addMapping(_corsMapping.path)
                        .allowedOriginPatterns(*_corsMapping.allowedOrigins.toTypedArray())
                        .allowedMethods(*_corsMapping.allowedMethods.toTypedArray())
                        .allowedHeaders(*_corsMapping.allowedHeaders.toTypedArray())
                        .exposedHeaders(*_corsMapping.exposedHeaders.toTypedArray())
                        .allowCredentials(_corsMapping.allowCredentials)
                }
            }
        }
    }

}
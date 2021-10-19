package com.easygoing.backend.services.core.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing
class JpaAuditConfiguration {

    @Bean
    fun auditorProvider() : AuditorAware<String> {
        //TODO should include authenticated user
        return AuditorAware{
            Optional.of("system")
        }
    }
}
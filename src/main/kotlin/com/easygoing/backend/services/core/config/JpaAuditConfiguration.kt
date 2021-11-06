package com.easygoing.backend.services.core.config

import com.easygoing.backend.services.user.dto.CustomUserDetails
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing
class JpaAuditConfiguration {

    @Bean
    fun auditorProvider() : AuditorAware<String> {
        return AuditorAware{
            Optional.of(
                SecurityContextHolder.getContext()?.authentication?.principal
                    ?.takeIf { it is CustomUserDetails }?.let { it as CustomUserDetails }?.userId?.toString()
                    ?: "system"
            )
        }
    }
}
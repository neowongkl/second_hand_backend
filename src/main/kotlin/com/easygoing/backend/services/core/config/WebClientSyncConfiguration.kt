package com.easygoing.backend.services.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
@ConfigurationProperties(prefix = "webclient.sync")
class WebClientSyncConfiguration {

    lateinit var timeout : Duration

}
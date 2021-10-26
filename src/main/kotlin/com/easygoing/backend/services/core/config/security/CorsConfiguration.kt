package com.easygoing.backend.services.core.config.security

import com.easygoing.backend.services.core.util.DataFormatHelper
import com.easygoing.backend.services.core.util.SystemHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.annotation.PostConstruct

@Configuration
class CorsConfiguration {

    @Autowired
    private lateinit var dataFormatHelper: DataFormatHelper

    @Autowired
    private lateinit var systemHelper: SystemHelper

    private var webSecurity: WebSecurity? = null

    @PostConstruct
    fun postConstruct(){
        val activeProfile = systemHelper.getActiveProfile()
        webSecurity = ClassPathResource("security/webSecurity-$activeProfile.json").file.let {
            dataFormatHelper.jsonFileToObject(it, WebSecurity::class.java)
        }
    }

    @Bean
    fun corsMapping(): WebMvcConfigurer? {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                webSecurity?.corsMappings?.forEach { _corsMapping->
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
package com.easygoing.backend.services.core.config.security.util

import com.easygoing.backend.services.core.config.security.dto.CorsMapping
import com.easygoing.backend.services.core.config.security.dto.Jwt
import com.easygoing.backend.services.core.config.security.dto.OAuth
import com.easygoing.backend.services.core.config.security.dto.WebSecurity
import com.easygoing.backend.services.core.util.DataFormatHelper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import javax.annotation.PostConstruct

@Configuration
@ConfigurationProperties(prefix = "webserity.cors")
class SecurityUtil {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var dataFormatHelper: DataFormatHelper

    lateinit var mappingpath : String

    lateinit var jwt : Jwt

    lateinit var corsMappings : List<CorsMapping>

    lateinit var oAuth: OAuth

    @PostConstruct
    fun postConstruct(){
        val webSecurity = ClassPathResource(mappingpath).file.let {
            dataFormatHelper.jsonFileToObject(it, WebSecurity::class.java)
        }
        if (webSecurity != null){
            corsMappings = webSecurity.corsMappings
            jwt = webSecurity.jwt
            oAuth = webSecurity.oAuth
        }else{
            logger.error("Web security file not found")
            throw Exception("Web security file not found")
        }
    }

     fun isAuthorizedRedirectUri(redirectUri: String?): Boolean {
         return redirectUri != null && oAuth.authorizedUrlList.contains(redirectUri)
    }
}
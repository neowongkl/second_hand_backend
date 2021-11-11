package com.easygoing.backend.services.core.config.security.handler

import com.easygoing.backend.services.core.config.security.constant.CookieKey
import com.easygoing.backend.services.core.config.security.repositroy.HttpCookieOAuth2AuthorizationRequestRepository
import com.easygoing.backend.services.core.config.security.util.CookieUtil
import com.easygoing.backend.services.core.config.security.util.SecurityUtil
import com.easygoing.backend.services.core.excpetion.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2AuthenticationFailureHandler: SimpleUrlAuthenticationFailureHandler() {

    @Autowired
    private lateinit var cookieUtil: CookieUtil

    @Autowired
    private lateinit var securityUtil: SecurityUtil

    @Autowired
    private lateinit var httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException
    ) {
        if ( request != null && response != null){
            var targetUrl: String? = cookieUtil.getCookie(request, CookieKey.REDIRECT_URI_PARAM.key)?.value
            if ( targetUrl == null && !securityUtil.isAuthorizedRedirectUri(targetUrl) ){
                throw BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
            }else{
                targetUrl = UriComponentsBuilder.fromUriString(targetUrl!!)
                    .queryParam("error", exception.localizedMessage)
                    .build().toUriString()
                httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
                redirectStrategy.sendRedirect(request, response, targetUrl)
            }

        }
    }
}
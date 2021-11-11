package com.easygoing.backend.services.core.config.security.handler

import com.easygoing.backend.services.core.config.security.constant.CookieKey
import com.easygoing.backend.services.core.config.security.repositroy.HttpCookieOAuth2AuthorizationRequestRepository
import com.easygoing.backend.services.core.config.security.util.CookieUtil
import com.easygoing.backend.services.core.config.security.util.JwtUtil
import com.easygoing.backend.services.core.config.security.util.SecurityUtil
import com.easygoing.backend.services.core.excpetion.BadRequestException
import com.easygoing.backend.services.user.dto.CustomUserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class OAuth2AuthenticationSuccessHandler: SimpleUrlAuthenticationSuccessHandler() {

    @Autowired
    private lateinit var cookieUtil: CookieUtil

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Autowired
    private lateinit var securityUtil: SecurityUtil

    @Autowired
    private lateinit var httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository

    val redirectUriCookieKey = CookieKey.REDIRECT_URI_PARAM.key

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        if ( request != null && response != null && authentication != null ){
            val targetUrl = getTargetUrl(request, response, authentication)

            if (response.isCommitted) {
                logger.debug("Response has already been committed. Unable to redirect to $targetUrl")
                return
            }

            clearAuthenticationAttributes(request, response)
            redirectStrategy.sendRedirect(request, response, targetUrl)
        }
    }


    fun getTargetUrl(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ): String? {
        val redirectUri = cookieUtil.getCookie(request, redirectUriCookieKey)?.value
        if ( !securityUtil.isAuthorizedRedirectUri(redirectUri) ) {
            throw BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
        }else{
            val targetUrl = redirectUri.toString()
            val jwt = jwtUtil.generateToken(authentication.principal as CustomUserDetails)
            response.addHeader("jwt", jwt)
            return UriComponentsBuilder.fromUriString(targetUrl)
                .build().toUriString()
        }
    }

    fun clearAuthenticationAttributes(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
    }

}
package com.easygoing.backend.services.core.config.security.repositroy

import com.easygoing.backend.services.core.config.security.constant.CookieKey
import com.easygoing.backend.services.core.config.security.util.CookieUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class HttpCookieOAuth2AuthorizationRequestRepository: AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    @Autowired
    private lateinit var cookieUtil: CookieUtil

    val authorizationRequestCookieKey = CookieKey.OAUTH2_AUTHORIZATION_REQUEST.key
    val requestUriCookieKey = CookieKey.REDIRECT_URI_PARAM.key
    private val cookieExpireSeconds = 180

    override fun loadAuthorizationRequest(request: HttpServletRequest?): OAuth2AuthorizationRequest? {
        return request?.let {  _request->
            cookieUtil.getCookie(_request, authorizationRequestCookieKey)?.let { _cookie->
                cookieUtil.deserialize(_cookie, OAuth2AuthorizationRequest::class.java)
            }
        }
    }

    override fun saveAuthorizationRequest(
        authorizationRequest: OAuth2AuthorizationRequest?,
        request: HttpServletRequest,
        response: HttpServletResponse?
    ) {
        if (authorizationRequest == null && response != null) {
            cookieUtil.deleteCookie(request, response, authorizationRequestCookieKey)
            cookieUtil.deleteCookie(request, response, requestUriCookieKey)
            return
        }
        if ( response != null ){
            cookieUtil.addCookie(
                response,
                authorizationRequestCookieKey,
                cookieUtil.serialize(authorizationRequest),
                cookieExpireSeconds
            )
            val redirectUriAfterLogin = request.getParameter(requestUriCookieKey)
            if ( redirectUriAfterLogin.isNotBlank() ) {
                cookieUtil.addCookie(response, requestUriCookieKey, redirectUriAfterLogin, cookieExpireSeconds)
            }
        }
    }

    override fun removeAuthorizationRequest(request: HttpServletRequest?): OAuth2AuthorizationRequest? {
        return loadAuthorizationRequest(request)
    }

    fun removeAuthorizationRequestCookies(request: HttpServletRequest, response: HttpServletResponse) {
        cookieUtil.deleteCookie(request, response, authorizationRequestCookieKey)
        cookieUtil.deleteCookie(request, response, requestUriCookieKey)
    }
}
package com.easygoing.backend.services.core.config.security.util

import org.springframework.stereotype.Component
import org.springframework.util.SerializationUtils
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CookieUtil {

    fun getCookie(request: HttpServletRequest, name: String): Cookie? {
        return request.cookies.firstOrNull{ it.name.equals(name) }
    }

    fun addCookie(response: HttpServletResponse, name: String, value: String, maxAge: Int) {
        val newCookie = Cookie(name, value).apply {
            this.path = "/"
            this.isHttpOnly = true
            this.maxAge = maxAge
        }
        response.addCookie(newCookie)
    }

    fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse, name: String) {
        request.cookies.firstOrNull{ it.name.equals(name) }?.let { _cookie->
            _cookie.value = ""
            _cookie.path = "/"
            _cookie.maxAge = 0
            response.addCookie(_cookie)
        }
    }

    fun serialize(`object`: Any?): String {
        return Base64.getUrlEncoder()
            .encodeToString(SerializationUtils.serialize(`object`))
    }

    fun <T> deserialize(cookie: Cookie, cls: Class<T>): T {
        return cls.cast(
            SerializationUtils.deserialize(
                Base64.getUrlDecoder().decode(cookie.value)
            )
        )
    }
}
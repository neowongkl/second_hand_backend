package com.easygoing.backend.services.core.config.security.filter

import com.easygoing.backend.services.core.config.security.util.JwtUtil
import com.easygoing.backend.services.user.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")
        if ( authorizationHeader!= null && authorizationHeader.startsWith("Bearer ") ){
            val jwt = authorizationHeader.substring(7)
            val userName = jwtUtil.extractUserName(jwt)

            if(SecurityContextHolder.getContext().authentication == null){
                val userDetails = customUserDetailsService.loadUserByUsername(userName)
                if ( userDetails != null && jwtUtil.validateToken(jwt, userDetails) == true ){
                    val token = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    token.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = token
                }
            }
        }
        filterChain.doFilter(request,response)
    }

}
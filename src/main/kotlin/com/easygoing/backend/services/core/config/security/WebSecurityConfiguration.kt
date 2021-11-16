package com.easygoing.backend.services.core.config.security

import com.easygoing.backend.services.core.config.security.entrypoint.JwtAuthenticationEntryPoint
import com.easygoing.backend.services.core.config.security.filter.JwtRequestFilter
import com.easygoing.backend.services.core.config.security.handler.OAuth2AuthenticationFailureHandler
import com.easygoing.backend.services.core.config.security.handler.OAuth2AuthenticationSuccessHandler
import com.easygoing.backend.services.core.config.security.repositroy.HttpCookieOAuth2AuthorizationRequestRepository
import com.easygoing.backend.services.core.config.security.util.JwtUtil
import com.easygoing.backend.services.user.service.CustomOAuth2UserServiceImpl
import com.easygoing.backend.services.user.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true,
)
class WebSecurityConfiguration: WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var jwtRequestFilter: JwtRequestFilter

    @Autowired
    private lateinit var jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint

    @Autowired
    private lateinit var customOAuth2UserServiceImpl: CustomOAuth2UserServiceImpl

    @Autowired
    private lateinit var oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler

    @Autowired
    private lateinit var oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler

    @Autowired
    private lateinit var httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository

    @Autowired
    private lateinit var clientRegistrationRepository: ClientRegistrationRepository

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Autowired
    private lateinit var oAuthConfiguration: OAuthConfiguration

    override fun configure(http: HttpSecurity?) {
        http?.also { _http->
            if (jwtUtil.enable){
                _http
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .exceptionHandling()//response to unauthorized access
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .and()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                    .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
                    .authorizeRequests().let {
                        if (jwtUtil.enable){
                            it.antMatchers("/authentication/**").permitAll()
                        }
                        if (oAuthConfiguration.enable){//ouath
                            it.antMatchers("/oauth2/authorize/**", "/oauth2/callback/**").permitAll()
                        }
                        it.anyRequest().authenticated().and()
                    }.also {
                        if (oAuthConfiguration.enable){//oauth
                            it.oauth2Login()
                                .clientRegistrationRepository(clientRegistrationRepository)//TODO enhance this part
                                .authorizationEndpoint()
                                .baseUri("/oauth2/authorize")
                                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                                .and()
                                .redirectionEndpoint()
                                .baseUri("/oauth2/callback/*")
                                .and()
                                .userInfoEndpoint()
                                .userService(customOAuth2UserServiceImpl)
                                .and()
                                .successHandler(oAuth2AuthenticationSuccessHandler)
                                .failureHandler(oAuth2AuthenticationFailureHandler)
                        }
                    }
//http://localhost:8080/api/oauth2/authorize/github?redirect_uri=http://localhost:3000/
            }else{
                _http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/**").permitAll()
            }

//            _http.authorizeRequests()
//                .antMatchers("/admin").hasRole("ADMIN")
//                .antMatchers("/user").hasAnyRole("USER","ADMIN")
//                .antMatchers("/").permitAll()
//                .and()
//                .formLogin()
        }
    }

    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsService

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.also { _auth->
            _auth.userDetailsService(customUserDetailsService)
        }
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun getPassEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder()
    }
}
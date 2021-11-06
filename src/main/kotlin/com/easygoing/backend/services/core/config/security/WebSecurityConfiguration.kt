package com.easygoing.backend.services.core.config.security

import com.easygoing.backend.services.core.config.security.filter.JwtRequestFilter
import com.easygoing.backend.services.core.config.security.util.SecurityUtil
import com.easygoing.backend.services.user.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class WebSecurityConfiguration: WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var jwtRequestFilter: JwtRequestFilter

    @Autowired
    private lateinit var securityUtil: SecurityUtil

    //restirction up to down, heavy to less
    override fun configure(http: HttpSecurity?) {
        http?.also { _http->
            if (securityUtil.jwt.enable){
                _http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/authentication/**").permitAll()
                    .anyRequest().authenticated()
                    .and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                _http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)

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
        //TODO change encoding

//        return BCryptPasswordEncoder()
        return NoOpPasswordEncoder.getInstance()
    }
}
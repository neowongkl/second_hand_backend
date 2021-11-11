package com.easygoing.backend.services.user.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User


class CustomUserDetails(
    val userId: Long?,
    val email: String,
    private val username: String,
    private val password: String,
    private val authorities: List<String>,
    private val enable: Boolean,
    val oAuthAttribute: MutableMap<String, Any>
    ) : UserDetails, OAuth2User {

    private val serialVersionUID = 1L

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.authorities.map { _authority->
            SimpleGrantedAuthority(_authority)
        }.toMutableList()
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.username
    }

    override fun isAccountNonExpired(): Boolean {
//        TODO("Not yet implemented")
        return true
    }

    override fun isAccountNonLocked(): Boolean {
//        TODO("Not yet implemented")
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
//        TODO("Not yet implemented")
        return true
    }

    override fun isEnabled(): Boolean {
        return this.enable
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return oAuthAttribute
    }

    override fun getName(): String {
        return this.username
    }

}
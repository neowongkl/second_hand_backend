package com.easygoing.backend.services.user.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    val userId: Long?,
    private val username: String,
    private val password: String,
    private val authorities: List<String>,
    private val enable: Boolean,
    ) : UserDetails {

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

}
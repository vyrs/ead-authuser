package com.ead.authuser.configs.security

import com.ead.authuser.models.UserModel
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.stream.Collectors

class UserDetailsImpl(
    val userId: UUID,
    private val fullName: String,
    private val username: String,

    @JsonIgnore
    private val password: String,
    private val email: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    companion object {
        fun build(userModel: UserModel): UserDetailsImpl {
            val authorities: List<GrantedAuthority> = userModel.roles.map { role -> SimpleGrantedAuthority(role.authority) }

            return UserDetailsImpl(
                userModel.userId!!,
                userModel.fullName,
                userModel.username,
                userModel.password,
                userModel.email,
                authorities
            )
        }
    }
}
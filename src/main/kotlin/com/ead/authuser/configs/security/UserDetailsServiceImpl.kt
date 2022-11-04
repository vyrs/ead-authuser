package com.ead.authuser.configs.security

import com.ead.authuser.models.UserModel
import com.ead.authuser.repositories.UserRepository
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val userModel: UserModel = userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User Not Found with username: $username") }
        return UserDetailsImpl.build(userModel)
    }

    @Throws(AuthenticationCredentialsNotFoundException::class)
    fun loadUserById(userId: UUID): UserDetails {
        val userModel = userRepository.findById(userId)
            .orElseThrow {
                AuthenticationCredentialsNotFoundException(
                    "User Not Found with userId: $userId"
                )
            }
        return UserDetailsImpl.build(userModel)
    }
}
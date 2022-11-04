package com.ead.authuser.configs.security

import com.ead.authuser.configs.EadLog
import com.ead.authuser.configs.log
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider: EadLog {
    @Value("\${ead.auth.jwtSecret}")
    private val jwtSecret: String? = null

    @Value("\${ead.auth.jwtExpirationMs}")
    private val jwtExpirationMs = 0
    
    fun generateJwt(authentication: Authentication): String {
        val userPrincipal: UserDetailsImpl = authentication.principal as UserDetailsImpl

//        val roles = userPrincipal.authorities.stream()
//            .map { role: GrantedAuthority -> role.authority }.collect(Collectors.joining(","))

        val roles = userPrincipal.authorities.joinToString(",")

        return Jwts.builder()
            .setSubject(userPrincipal.userId.toString())
            .claim("roles", roles)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getSubjectJwt(token: String): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject
    }

    fun validateJwt(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            log().error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            log().error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            log().error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            log().error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            log().error("JWT claims string is empty: {}", e.message)
        }
        return false
    }
}
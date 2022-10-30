package com.ead.authuser.models

import com.ead.authuser.enums.RoleType
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "TB_ROLES")
class RoleModel(
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 30)
    private val roleName: RoleType

) : GrantedAuthority, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val roleId: UUID? = null

    @JsonIgnore
    override fun getAuthority(): String {
        return roleName.toString()
    }
}
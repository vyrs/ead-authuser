package com.ead.authuser.repositories

import com.ead.authuser.enums.RoleType
import com.ead.authuser.models.RoleModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID
import java.util.Optional


interface RoleRepository : JpaRepository<RoleModel, UUID> {
    fun findByRoleName(name: RoleType): Optional<RoleModel>
}
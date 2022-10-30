package com.ead.authuser.services

import com.ead.authuser.enums.RoleType

import com.ead.authuser.models.RoleModel
import java.util.Optional


interface RoleService {
    fun findByRoleName(roleType: RoleType): Optional<RoleModel>
}
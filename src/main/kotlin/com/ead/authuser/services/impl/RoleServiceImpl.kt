package com.ead.authuser.services.impl

import com.ead.authuser.enums.RoleType
import com.ead.authuser.models.RoleModel
import com.ead.authuser.repositories.RoleRepository
import com.ead.authuser.services.RoleService
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class RoleServiceImpl(private val roleRepository: RoleRepository) : RoleService {
    override fun findByRoleName(roleType: RoleType): Optional<RoleModel> {
        return roleRepository.findByRoleName(roleType)
    }
}
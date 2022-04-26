package com.ead.authuser.repositories

import com.ead.authuser.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID

interface UserRepository: JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

    fun existsByUsername(userName: String): Boolean
    fun existsByEmail(email: String): Boolean
}
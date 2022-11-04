package com.ead.authuser.repositories

import com.ead.authuser.models.UserModel
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*


interface UserRepository: JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

    fun existsByUsername(userName: String): Boolean
    fun existsByEmail(email: String): Boolean

    @EntityGraph(attributePaths = ["roles"], type = EntityGraph.EntityGraphType.FETCH)
    fun findByUsername(username: String): Optional<UserModel>

    @EntityGraph(attributePaths = ["roles"], type = EntityGraph.EntityGraphType.FETCH)
    override fun findById(userId: UUID): Optional<UserModel>
}
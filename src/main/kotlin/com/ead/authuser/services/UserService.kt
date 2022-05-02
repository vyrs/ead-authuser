package com.ead.authuser.services

import com.ead.authuser.models.UserModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import java.util.*


interface UserService {
    fun findAll(): List<UserModel>?
    fun findById(userId: UUID): Optional<UserModel>
    fun deleteUser(userModel: UserModel)
    fun save(userModel: UserModel): UserModel
    fun existsByUsername(userName: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findAll(spec: Specification<UserModel>, pageable: Pageable): Page<UserModel>?
//    fun findAll(pageable: Pageable): Page<UserModel>
}
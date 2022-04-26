package com.ead.authuser.services

import com.ead.authuser.models.UserModel
import java.util.*


interface UserService {
    fun findAll(): List<UserModel>?
    fun findById(userId: UUID): Optional<UserModel>
    fun deleteUser(userModel: UserModel)
    fun save(userModel: UserModel): UserModel
    fun existsByUsername(userName: String): Boolean
    fun existsByEmail(email: String): Boolean
}
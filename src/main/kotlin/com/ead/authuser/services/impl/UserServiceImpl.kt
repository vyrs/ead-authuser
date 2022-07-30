package com.ead.authuser.services.impl

import com.ead.authuser.clients.CourseClient
import com.ead.authuser.models.UserModel
import com.ead.authuser.repositories.UserRepository
import com.ead.authuser.services.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val courseClient: CourseClient
): UserService {

    override fun findAll(): List<UserModel>? =
        userRepository.findAll()

    override fun findAll(spec: Specification<UserModel>?, pageable: Pageable): Page<UserModel> {
        return userRepository.findAll(spec, pageable)
    }

    override fun findById(userId: UUID): Optional<UserModel> =
        userRepository.findById(userId)

    @Transactional
    override fun deleteUser(userModel: UserModel) {
        userRepository.delete(userModel)
    }

    override fun save(userModel: UserModel): UserModel =
        userRepository.save(userModel)

    override fun existsByUsername(userName: String): Boolean =
        userRepository.existsByUsername(userName)

    override fun existsByEmail(email: String): Boolean =
        userRepository.existsByEmail(email)

}

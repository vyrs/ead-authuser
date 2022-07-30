package com.ead.authuser.services.impl

import com.ead.authuser.models.UserCourseModel
import com.ead.authuser.models.UserModel
import com.ead.authuser.repositories.UserCourseRepository
import com.ead.authuser.services.UserCourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID
import javax.transaction.Transactional


@Service
class UserCourseServiceImpl(private val userCourseRepository: UserCourseRepository) : UserCourseService {

    override fun existsByUserAndCourseId(userModel: UserModel, courseId: UUID): Boolean {
        return userCourseRepository.existsByUserAndCourseId(userModel, courseId)
    }

    override fun save(userCourseModel: UserCourseModel): UserCourseModel {
        return userCourseRepository.save(userCourseModel)
    }

    override fun existsByCourseId(courseId: UUID): Boolean {
        return userCourseRepository.existsByCourseId(courseId)
    }

    @Transactional
    override fun deleteUserCourseByCourse(courseId: UUID) {
        userCourseRepository.deleteAllByCourseId(courseId)
    }
}
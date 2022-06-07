package com.ead.authuser.repositories

import com.ead.authuser.models.UserCourseModel
import com.ead.authuser.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID


interface UserCourseRepository : JpaRepository<UserCourseModel, UUID?> {
    fun existsByUserAndCourseId(userModel: UserModel, courseId: UUID): Boolean

    @Query(value = "select * from tb_users_courses where user_user_id = :userId", nativeQuery = true)
    fun findAllUserCourseIntoUser(@Param("userId") userId: UUID): List<UserCourseModel>
}
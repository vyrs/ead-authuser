package com.ead.authuser.dtos

import com.ead.authuser.enums.CourseLevel
import com.ead.authuser.enums.CourseStatus
import java.util.UUID

data class CourseDto(
    val courseId: UUID,
    val name: String,
    val description: String,
    val imageUrl: String?,
    val courseStatus: CourseStatus,
    val userInstructor: UUID?,
    val courseLevel: CourseLevel
)

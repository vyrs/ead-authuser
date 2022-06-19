package com.ead.authuser.dtos

import com.ead.authuser.enums.CourseLevel
import com.ead.authuser.enums.CourseStatus
import java.util.UUID

data class CourseDto(
    private val courseId: UUID,
    private val name: String,
    private val description: String,
    private val imageUrl: String,
    private val courseStatus: CourseStatus,
    private val userInstructor: UUID,
    private val courseLevel: CourseLevel
)

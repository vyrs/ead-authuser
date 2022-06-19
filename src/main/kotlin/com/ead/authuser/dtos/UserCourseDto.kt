package com.ead.authuser.dtos

import java.util.UUID

data class UserCourseDto(
    private val userId: UUID,
    private val courseId: UUID
)

package com.ead.authuser.services

import org.springframework.data.domain.Pageable
import java.util.UUID

interface UtilsService {
    fun createUrlGetAllCoursesByUser(userId: UUID, pageable: Pageable): String
}
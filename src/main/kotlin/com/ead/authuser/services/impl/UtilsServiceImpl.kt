package com.ead.authuser.services.impl

import com.ead.authuser.services.UtilsService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UtilsServiceImpl : UtilsService {
    override fun createUrlGetAllCoursesByUser(userId: UUID, pageable: Pageable): String {
        return ("/courses?userId=" + userId + "&page=" + pageable.pageNumber + "&size="
                + pageable.pageSize + "&sort=" + pageable.sort.toString().replace(": ".toRegex(), ","))
    }
}
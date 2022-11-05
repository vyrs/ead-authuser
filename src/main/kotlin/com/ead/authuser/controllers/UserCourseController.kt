package com.ead.authuser.controllers

import com.ead.authuser.clients.CourseClient
import com.ead.authuser.services.UserService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class UserCourseController(private val courseClient: CourseClient, private val userService: UserService) {

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/users/{userId}/courses")
    fun getAllCoursesByUser(
        @PageableDefault(page = 0, size = 10, sort = ["courseId"], direction = Sort.Direction.ASC) pageable: Pageable,
        @PathVariable(value = "userId") userId: UUID,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<Any> {
        val userModelOptional = userService.findById(userId)

        if (!userModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.")
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable, token))
    }
}
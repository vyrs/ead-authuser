package com.ead.authuser.controllers

import com.ead.authuser.clients.CourseClient
import com.ead.authuser.dtos.CourseDto
import com.ead.authuser.dtos.UserCourseDto
import com.ead.authuser.services.UserCourseService
import com.ead.authuser.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class UserCourseController(private val courseClient: CourseClient, private val userService: UserService, private val userCourseService: UserCourseService) {

    @GetMapping("/users/{userId}/courses")
    fun getAllCoursesByUser(
        @PageableDefault(page = 0, size = 10, sort = ["courseId"], direction = Sort.Direction.ASC) pageable: Pageable,
        @PathVariable(value = "userId") userId: UUID
    ): ResponseEntity<Page<CourseDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable))
    }

    @PostMapping("/users/{userId}/courses/subscription")
    fun saveSubscriptionUserInCourse(
        @PathVariable(value = "userId") userId: UUID,
        @RequestBody userCourseDto: @Valid UserCourseDto
    ): ResponseEntity<Any> {
        val userModelOptional = userService.findById(userId)
        if (!userModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.")
        }
        if (userCourseService.existsByUserAndCourseId(userModelOptional.get(), userCourseDto.courseId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!")
        }
        val userCourseModel =
            userCourseService.save(userModelOptional.get().convertToUserCourseModel(userCourseDto.courseId))
        return ResponseEntity.status(HttpStatus.CREATED).body(userCourseModel)
    }
}
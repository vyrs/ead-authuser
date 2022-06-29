package com.ead.authuser.controllers

import com.ead.authuser.dtos.InstructorDto
import com.ead.authuser.enums.UserType
import com.ead.authuser.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneId
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/instructors")
class InstructorController(private val userService: UserService) {

    @PostMapping("/subscription")
    fun saveSubscriptionInstructor(@RequestBody instructorDto: @Valid InstructorDto): ResponseEntity<Any> {
        val userModelOptional = userService.findById(instructorDto.userId)
        return if (!userModelOptional.isPresent) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.")
        } else {
            val userModel = userModelOptional.get()
            userModel.userType = UserType.INSTRUCTOR
            userModel.lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"))
            userService.save(userModel)
            ResponseEntity.status(HttpStatus.OK).body(userModel)
        }
    }
}
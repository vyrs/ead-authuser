package com.ead.authuser.controllers

import com.ead.authuser.dtos.UserDto
import com.ead.authuser.dtos.toModel
import com.ead.authuser.enums.UserStatus
import com.ead.authuser.enums.UserType
import com.ead.authuser.models.UserModel
import com.ead.authuser.services.UserService
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.beans.BeanUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneId


@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/auth")
class AuthenticationController(private val userService: UserService) {

    @PostMapping("/signup")
    fun registerUser(@RequestBody @JsonView(UserDto.UserView.RegistrationPost::class) userDto: UserDto): ResponseEntity<Any> {

        if(userService.existsByUsername(userDto.username!!)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
        }
        if(userService.existsByEmail(userDto.email!!)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
        }

        val userModel = userDto.toModel()
        userService.save(userModel)
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel)
    }

}
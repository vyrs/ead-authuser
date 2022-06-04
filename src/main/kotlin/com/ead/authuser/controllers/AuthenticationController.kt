package com.ead.authuser.controllers

import com.ead.authuser.configs.EadLog
import com.ead.authuser.configs.log
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
class AuthenticationController(private val userService: UserService):EadLog {

    @PostMapping("/signup")
    fun registerUser(@RequestBody @Validated(UserDto.UserView.RegistrationPost::class) @JsonView(UserDto.UserView.RegistrationPost::class) userDto: UserDto): ResponseEntity<Any> {
        log().debug("POST registerUser userDto received {} ", userDto.toString())

        if(userService.existsByUsername(userDto.username!!)){
            log().warn("Username {} is Already Taken ", userDto.username)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
        }
        if(userService.existsByEmail(userDto.email!!)){
            log().warn("Email {} is Already Taken ", userDto.email)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
        }

        val userModel = userDto.toModel()
        userService.save(userModel)
        log().debug("POST registerUser userModel saved {} ", userModel.toString())
        log().info("User saved successfully userId {} ", userModel.userId)
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel)
    }
}

package com.ead.authuser.controllers

import com.ead.authuser.configs.EadLog
import com.ead.authuser.configs.log
import com.ead.authuser.dtos.UserDto
import com.ead.authuser.models.UserModel
import com.ead.authuser.services.UserService
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/users")
class UserController(private val userService: UserService): EadLog {

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserModel>> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll())
    }

    @GetMapping("/{userId}")
    fun getOneUser(@PathVariable userId: UUID): ResponseEntity<Any> {
        val userModelOptional = userService.findById(userId)

        return if(!userModelOptional.isPresent){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } else{
            ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
        }
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: UUID): ResponseEntity<Any> {
        log().debug("DELETE deleteUser userId received {} ", userId)
        val userModelOptional: Optional<UserModel> = userService.findById(userId)
        return if (!userModelOptional.isPresent) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.")
        } else {
            userService.deleteUser(userModelOptional.get())
            log().debug("DELETE deleteUser userId deleted {} ", userId)
            log().info("User deleted successfully userId {} ", userId)
            ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.")
        }
    }

    @PutMapping("/{userId}")
    fun updateUser(
        @PathVariable userId: UUID,
        @RequestBody @Validated(UserDto.UserView.UserPut::class) @JsonView(UserDto.UserView.UserPut::class) userDto: UserDto
    ): ResponseEntity<Any> {
        log().debug("UPDATE updateUser userId received {} ", userId)
        val userModelOptional: Optional<UserModel> = userService.findById(userId)

        return if (!userModelOptional.isPresent) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.")
        } else {
            val userModel = userModelOptional.get()

            userModel.fullName = userDto.fullName!!
            userModel.phoneNumber = userDto.phoneNumber!!
            userModel.cpf = userDto.cpf!!
            userModel.lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"))

            userService.save(userModel)

            ResponseEntity.status(HttpStatus.OK).body(userModel)
        }
    }

    @PutMapping("/{userId}/password")
    fun updatePassword(
        @PathVariable userId: UUID,
        @RequestBody @Validated(UserDto.UserView.PasswordPut::class) @JsonView(UserDto.UserView.PasswordPut::class) userDto: UserDto
    ): ResponseEntity<Any> {
        log().debug("UPDATE updatePassword userId received {} ", userId)
        val userModelOptional: Optional<UserModel> = userService.findById(userId)


        if (!userModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.")
        }
        return if(userModelOptional.get().password != userDto.oldPassword){
            ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password!")
        } else {
            val userModel = userModelOptional.get()

            userModel.password = userDto.password!!
            userModel.lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"))

            userService.save(userModel)
            ResponseEntity.status(HttpStatus.OK).body("Password updated successfully.")
        }
    }

    @PutMapping("/{userId}/image")
    fun updateImage(
        @PathVariable userId: UUID,
        @RequestBody @Validated(UserDto.UserView.ImagePut::class) @JsonView(UserDto.UserView.ImagePut::class) userDto: UserDto
    ): ResponseEntity<Any> {
        log().debug("UPDATE updateImage userId received {} ", userId)
        val userModelOptional: Optional<UserModel> = userService.findById(userId)

        return if (!userModelOptional.isPresent) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.")
        } else {
            val userModel = userModelOptional.get()

            userModel.imageUrl = userDto.imageUrl
            userModel.lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"))

            userService.save(userModel)
            ResponseEntity.status(HttpStatus.OK).body(userModel)
        }
    }


}
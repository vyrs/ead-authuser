package com.ead.authuser.controllers

import com.ead.authuser.configs.EadLog
import com.ead.authuser.configs.log
import com.ead.authuser.dtos.UserDto
import com.ead.authuser.models.UserModel
import com.ead.authuser.services.UserService
import com.ead.authuser.specifications.SpecificationTemplate.UserSpec
import com.ead.authuser.specifications.SpecificationTemplate.userCourseId
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
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
    fun getAllUsers(spec: UserSpec?,
                    @PageableDefault(page = 0, size = 10, sort = ["userId"],
                        direction = Sort.Direction.ASC) pageable: Pageable
    ): ResponseEntity<Page<UserModel>> {

        val userModelPage = userService.findAll(spec, pageable)
            .map { user -> user.add(linkTo(methodOn(UserController::class.java).getOneUser(user.userId!!)).withSelfRel()) }

        return ResponseEntity.status(HttpStatus.OK).body(userModelPage)
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
        log().debug("PUT updateUser userId received {} ", userId)
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

            log().debug("PUT updateUser userId saved {} ", userModel.userId)
            log().info("User updated successfully userId {} ", userModel.userId)
            ResponseEntity.status(HttpStatus.OK).body(userModel)
        }
    }

    @PutMapping("/{userId}/password")
    fun updatePassword(
        @PathVariable userId: UUID,
        @RequestBody @Validated(UserDto.UserView.PasswordPut::class) @JsonView(UserDto.UserView.PasswordPut::class) userDto: UserDto
    ): ResponseEntity<Any> {
        log().debug("PUT updatePassword userDto received {} ", userDto.toString())
        val userModelOptional: Optional<UserModel> = userService.findById(userId)


        if (!userModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.")
        }
        return if(userModelOptional.get().password != userDto.oldPassword){
            log().warn("Mismatched old password userId {} ", userId)
            ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password!")
        } else {
            val userModel = userModelOptional.get()

            userModel.password = userDto.password!!
            userModel.lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"))

            userService.save(userModel)

            log().debug("PUT updatePassword userId saved {} ", userModel.userId)
            log().info("Password updated successfully userId {} ", userModel.userId)
            ResponseEntity.status(HttpStatus.OK).body("Password updated successfully.")
        }
    }

    @PutMapping("/{userId}/image")
    fun updateImage(
        @PathVariable userId: UUID,
        @RequestBody @Validated(UserDto.UserView.ImagePut::class) @JsonView(UserDto.UserView.ImagePut::class) userDto: UserDto
    ): ResponseEntity<Any> {
        log().debug("PUT updateImage userDto received {} ", userDto.toString())
        val userModelOptional: Optional<UserModel> = userService.findById(userId)

        return if (!userModelOptional.isPresent) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.")
        } else {
            val userModel = userModelOptional.get()

            userModel.imageUrl = userDto.imageUrl
            userModel.lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"))

            userService.save(userModel)
            log().debug("PUT updateImage userId saved {} ", userModel.userId)
            log().info("Image updated successfully userId {} ", userModel.userId)
            ResponseEntity.status(HttpStatus.OK).body(userModel)
        }
    }


}
package com.ead.authuser.dtos

import com.ead.authuser.enums.UserStatus
import com.ead.authuser.enums.UserType
import com.ead.authuser.models.UserModel
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonView
import java.time.LocalDateTime
import java.time.ZoneId

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDto(
    @JsonView(UserView.RegistrationPost::class)
    val username: String?,

    @JsonView(UserView.RegistrationPost::class)
    val email: String?,

    @JsonView(UserView.RegistrationPost::class, UserView.PasswordPut::class)
    val password: String?,

    @JsonView(UserView.PasswordPut::class)
    val oldPassword: String?,

    @JsonView(UserView.RegistrationPost::class, UserView.UserPut::class)
    val fullName: String?,

    @JsonView(UserView.RegistrationPost::class, UserView.UserPut::class)
    val phoneNumber: String?,

    @JsonView(UserView.RegistrationPost::class, UserView.UserPut::class)
    val cpf: String?,

    @JsonView(UserView.ImagePut::class)
    val imageUrl: String?
) {
    interface UserView {
        interface RegistrationPost
        interface UserPut
        interface PasswordPut
        interface ImagePut
    }
}

fun UserDto.toModel() =
    UserModel(
        username!!,
        email!!,
        password!!,
        fullName!!,
        UserStatus.ACTIVE,
        UserType.STUDENT,
        phoneNumber!!,
        cpf!!,
        imageUrl,
        LocalDateTime.now(ZoneId.of("UTC")),
        LocalDateTime.now(ZoneId.of("UTC"))
    )
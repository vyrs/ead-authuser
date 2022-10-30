package com.ead.authuser.dtos

import com.ead.authuser.enums.UserStatus
import com.ead.authuser.enums.UserType
import com.ead.authuser.models.RoleModel
import com.ead.authuser.models.UserModel
import com.ead.authuser.validation.UsernameConstraint
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonView
import java.time.LocalDateTime
import java.time.ZoneId
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDto(
    @field:NotBlank(groups = [UserView.RegistrationPost::class])
    @field:Size(min = 4, max = 50, groups = [UserView.RegistrationPost::class])
    @UsernameConstraint(groups = [UserView.RegistrationPost::class])
    @JsonView(UserView.RegistrationPost::class)
    val username: String?,

    @field:NotBlank(groups = [UserView.RegistrationPost::class])
    @field:Email(groups = [UserView.RegistrationPost::class])
    @JsonView(UserView.RegistrationPost::class)
    val email: String?,

    @field:NotBlank(groups = [UserView.RegistrationPost::class, UserView.PasswordPut::class])
    @field:Size(min = 6, max = 20, groups = [UserView.RegistrationPost::class, UserView.PasswordPut::class])
    @JsonView(UserView.RegistrationPost::class, UserView.PasswordPut::class)
    val password: String?,

    @field:NotBlank(groups = [UserView.PasswordPut::class])
    @field:Size(min = 6, max = 20, groups = [UserView.PasswordPut::class])
    @JsonView(UserView.PasswordPut::class)
    val oldPassword: String?,

    @JsonView(UserView.RegistrationPost::class, UserView.UserPut::class)
    val fullName: String?,

    @JsonView(UserView.RegistrationPost::class, UserView.UserPut::class)
    val phoneNumber: String?,

    @JsonView(UserView.RegistrationPost::class, UserView.UserPut::class)
    val cpf: String?,

    @field:NotBlank(groups = [UserView.ImagePut::class])
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

fun UserDto.toModel(role: RoleModel, passwordEncoded: String) =
    UserModel(
        username!!,
        email!!,
        passwordEncoded,
        fullName!!,
        UserStatus.ACTIVE,
        UserType.STUDENT,
        phoneNumber!!,
        cpf!!,
        imageUrl,
        LocalDateTime.now(ZoneId.of("UTC")),
        LocalDateTime.now(ZoneId.of("UTC")),
        setOf(role)
    )
package com.ead.authuser.models

import com.ead.authuser.enums.UserStatus
import com.ead.authuser.enums.UserType
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.RepresentationModel
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
class UserModel(
    @Column(nullable = false, unique = true, length = 50)
    val username: String,
    @Column(nullable = false, unique = true, length = 50)
    val email: String,
    @Column(nullable = false, length = 255)
    @JsonIgnore
    var password: String,
    @Column(nullable = false, length = 150)
    var fullName: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val userStatus: UserStatus,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val userType: UserType,
    @Column(length = 20)
    var phoneNumber: String,
    @Column(length = 20)
    var cpf: String,
    var imageUrl: String?,
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    val creationDate: LocalDateTime,
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    var lastUpdateDate: LocalDateTime
): RepresentationModel<UserModel>() {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val userId: UUID? = null
}
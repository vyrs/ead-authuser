package com.ead.authuser.models

import com.ead.authuser.dtos.UserEventDto
import com.ead.authuser.enums.UserStatus
import com.ead.authuser.enums.UserType
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.BeanUtils
import org.springframework.hateoas.RepresentationModel
import java.time.LocalDateTime
import java.util.*
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
    var userType: UserType,
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
    var lastUpdateDate: LocalDateTime,


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(    name = "TB_USERS_ROLES",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableSet<RoleModel> = HashSet()
): RepresentationModel<UserModel>() {
    fun convertToUserEventDto(): UserEventDto {
        val userEventDto = UserEventDto()
        BeanUtils.copyProperties(this, userEventDto)
        userEventDto.userType = this.userType.toString()
        userEventDto.userStatus = this.userStatus.toString()
        return userEventDto
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val userId: UUID? = null
}
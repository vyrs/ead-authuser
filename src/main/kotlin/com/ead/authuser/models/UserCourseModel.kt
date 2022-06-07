package com.ead.authuser.models

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS_COURSES")
class UserCourseModel(
    @Column(nullable = false)
    private val courseId: UUID,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private val user: UserModel
) {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
}
package com.ead.authuser.dtos

import java.util.UUID

data class UserEventDto(
    var userId: UUID? = null,
    var username: String? = null,
    var email: String? = null,
    var fullName: String? = null,
    var userStatus: String? = null,
    var userType: String? = null,
    var phoneNumber: String? = null,
    var cpf: String? = null,
    var imageUrl: String? = null,
    var actionType: String? = null
)

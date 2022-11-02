package com.ead.authuser.dtos

data class JwtDto(
    val token: String,
    val type: String = "Bearer"
)
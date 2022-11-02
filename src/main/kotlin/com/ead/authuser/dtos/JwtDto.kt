package com.ead.authuser.dtos

import com.fasterxml.jackson.annotation.JsonInclude

data class JwtDto(
    val token: String,
    val type: String = "Bearer"
)
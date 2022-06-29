package com.ead.authuser.dtos

import java.util.UUID
import javax.validation.constraints.NotNull

data class InstructorDto(
    @field:NotNull
    val userId: UUID
)

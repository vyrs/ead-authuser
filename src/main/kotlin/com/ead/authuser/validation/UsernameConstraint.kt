package com.ead.authuser.validation

import com.ead.authuser.dtos.UserDto
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UsernameConstraintImpl::class])
@MustBeDocumented
annotation class UsernameConstraint(
    val message: String = "Invalid username!",
    val groups: Array<KClass<UserDto.UserView.RegistrationPost>> = [],
    val payload: Array<KClass<Payload>> = []
)
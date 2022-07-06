package com.ead.authuser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class AuthuserApplication

fun main(args: Array<String>) {
    runApplication<AuthuserApplication>(*args)
}

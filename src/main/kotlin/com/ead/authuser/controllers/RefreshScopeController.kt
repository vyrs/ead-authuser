package com.ead.authuser.controllers

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RefreshScope
class RefreshScopeController {
    @Value("\${authuser.refreshscope.name}")
    private val name: String? = null

    @RequestMapping("/refreshscope")
    fun refreshscope(): String {
        return name!!
    }
}
package com.ead.authuser.configs

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
class RestTemplateConfig {

    val TIMEOUT = 5000L

    @LoadBalanced
    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        // Do any additional configuration here
        return builder
            .setConnectTimeout(Duration.ofMillis(TIMEOUT))
            .setReadTimeout(Duration.ofMillis(TIMEOUT))
            .build()
    }
}

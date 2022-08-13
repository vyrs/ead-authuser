package com.ead.authuser.clients

import com.ead.authuser.configs.EadLog
import com.ead.authuser.configs.log
import com.ead.authuser.dtos.CourseDto
import com.ead.authuser.dtos.ResponsePageDto
import com.ead.authuser.services.UtilsService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import java.util.*


@Component
class CourseClient(private val restTemplate: RestTemplate, private val utilsService: UtilsService): EadLog {

    @Value("\${ead.api.url.course}")
    lateinit var REQUEST_URL_COURSE: String

//    @Retry(name = "retryInstance", fallbackMethod = "retryfallback")
    @CircuitBreaker(name = "circuitbreakerInstance")
    fun getAllCoursesByUser(userId: UUID, pageable: Pageable): Page<CourseDto> {
        var result: ResponseEntity<ResponsePageDto<CourseDto>>? = null
        val url = REQUEST_URL_COURSE + utilsService.createUrlGetAllCoursesByUser(userId, pageable)
        log().debug("Request URL: {} ", url)
        log().info("Request URL: {} ", url)
        try {
            val responseType: ParameterizedTypeReference<ResponsePageDto<CourseDto>> =
                object : ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {}
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType)
            val searchResult = result.body!!.content
            log().debug("Response Number of Elements: {} ", searchResult.size)
        } catch (e: HttpStatusCodeException) {
            log().error("Error request /courses {} ", e)
        }
        log().info("Ending request /courses userId {} ", userId)
        return result!!.body!!
    }

    fun circuitbreakerfallback(userId: UUID, pageable: Pageable, t: Throwable): Page<CourseDto> {
        log().error("Inside circuit breaker fallback, cause - {}", t.toString())
        val searchResult: List<CourseDto> = ArrayList()
        return PageImpl(searchResult)
    }

    fun retryfallback(userId: UUID, pageable: Pageable, t: Throwable): Page<CourseDto> {
        log().error("Inside retry retryfallback, cause - {}", t.toString())
        val searchResult: List<CourseDto> = ArrayList()
        return PageImpl(searchResult)
    }
}
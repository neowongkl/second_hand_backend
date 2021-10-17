package com.easygoing.backend.services.sample.controller

import com.easygoing.backend.services.sample.dao.SampleMariaDb
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SampleMariaDbControllerTest {

    @Bean
    fun restTemplate(): RestTemplate? {
        return RestTemplate()
    }

    val url = "http://localhost:8080/api/samplemaria"

    @Test
    fun listAll() {
        val responseEntity = restTemplate()!!.getForEntity(url, Array<SampleMariaDb>::class.java)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
    }

    @Test
    fun getById() {
        val responseEntity = restTemplate()!!.getForEntity("$url/1", SampleMariaDb::class.java)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
    }

}
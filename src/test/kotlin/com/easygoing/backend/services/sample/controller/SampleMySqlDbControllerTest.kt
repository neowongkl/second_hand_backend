package com.easygoing.backend.services.sample.controller

import com.easygoing.backend.services.sample.dao.SampleMariaDb
import com.easygoing.backend.services.sample.dao.SampleMySqlDb
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SampleMySqlDbControllerTest {

    @Bean
    fun restTemplate(): RestTemplate? {
        return RestTemplate()
    }

    val url = "http://localhost:8080/api/samplemysql"

    @Test
    fun listAll() {
        val responseEntity = restTemplate()!!.getForEntity(url, Array<SampleMySqlDb>::class.java)
        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertNotNull(responseEntity.body)
    }
}
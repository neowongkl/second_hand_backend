package com.easygoing.backend.services.sample.controller

import com.easygoing.backend.services.core.util.DataFormatHelper
import com.easygoing.backend.services.sample.dao.SampleMariaDb
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
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

    @Autowired
    private lateinit var dataFormatHelper: DataFormatHelper

    val url = "http://localhost:8080/api/samplemaria"

    var dummyId = 0L

    @Test
    fun listAll() {
        val responseEntity = restTemplate()!!.getForEntity(url, Array<SampleMariaDb>::class.java)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
        responseEntity.body?.let {
            dummyId = it[0].id ?: -1
        }
    }

    @Test
    fun getById() {
        val responseEntity = restTemplate()!!.getForEntity("$url/1", SampleMariaDb::class.java)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
    }

}
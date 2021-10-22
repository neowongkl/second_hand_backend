package com.easygoing.backend.services.sample.controller

import com.easygoing.backend.services.sample.dao.SampleMySqlDb
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.reactive.function.client.WebClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SampleMySqlDbControllerTest {

    private val webClient = WebClient.create()

    val url = "http://localhost:8080/api/samplemysql"

    @Test
    fun listAll() {
        val entity = webClient.get().uri(url)
            .exchangeToMono { response->
                if ( response.statusCode().is2xxSuccessful ){
                    return@exchangeToMono response.bodyToMono(Array<SampleMySqlDb>::class.java)
                }else{
                    return@exchangeToMono null
                }
            }.block()
        assertNotNull(entity)
    }
}
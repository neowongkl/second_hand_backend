package com.easygoing.backend.services.sample.controller

import com.easygoing.backend.services.sample.dao.SampleMySqlDb
import com.easygoing.backend.services.sample.service.SampleMySqlDbService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["samplemysql"])
class SampleMySqlDbController {

    @Autowired
    private lateinit var sampleMySqlDbService: SampleMySqlDbService

    @RequestMapping(method = [RequestMethod.GET])
    fun listAll(): List<SampleMySqlDb>{
        return sampleMySqlDbService.listAll()
    }

}
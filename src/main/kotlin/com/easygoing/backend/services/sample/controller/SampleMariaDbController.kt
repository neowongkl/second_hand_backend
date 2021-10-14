package com.easygoing.backend.services.sample.controller

import com.easygoing.backend.services.sample.dao.SampleMariaDb
import com.easygoing.backend.services.sample.service.SampleMariaDbService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["samplemaria"])
class SampleMariaDbController {

    @Autowired
    private lateinit var sampleMariaDbService: SampleMariaDbService

    @RequestMapping(method = [RequestMethod.GET])
    fun listAll(): List<SampleMariaDb>{
        return sampleMariaDbService.listAll()
    }

}
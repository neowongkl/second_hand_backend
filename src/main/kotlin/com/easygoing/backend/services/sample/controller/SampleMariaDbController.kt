package com.easygoing.backend.services.sample.controller

import com.easygoing.backend.services.sample.dto.SampleMariaDbRequestDto
import com.easygoing.backend.services.sample.dto.SampleMariaDbResponseDto
import com.easygoing.backend.services.sample.service.SampleMariaDbService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["samplemaria"])
class SampleMariaDbController {

    @Autowired
    private lateinit var sampleMariaDbService: SampleMariaDbService

    @RequestMapping(method = [RequestMethod.GET])
    fun listAll(): List<SampleMariaDbResponseDto>{
        return sampleMariaDbService.listAll()
    }

    //TODO getbyid
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Long) : SampleMariaDbResponseDto?{
        return sampleMariaDbService.findById(id)
    }

    //TODO create
    @RequestMapping(method = [RequestMethod.POST])
    fun create(@RequestBody sampleMariaDbRequestDto: SampleMariaDbRequestDto): SampleMariaDbResponseDto{
        return sampleMariaDbService.create(sampleMariaDbRequestDto)
    }

    //TODO delete
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable(value = "id")id: Long): SampleMariaDbResponseDto?{
        return sampleMariaDbService.deleteById(id)
    }

}
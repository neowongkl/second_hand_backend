package com.easygoing.backend.services.sample.controller

import com.easygoing.backend.services.sample.dto.SampleRequestDto
import com.easygoing.backend.services.sample.dto.SampleResponseDto
import com.easygoing.backend.services.sample.service.SampleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("sample")
class SampleController {

    @Autowired
    private lateinit var sampleService: SampleService

    @RequestMapping( method = [RequestMethod.GET])
    fun listAll() : List<SampleResponseDto>{
        return sampleService.listAll()
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Long): SampleResponseDto?{
        return sampleService.getById(id)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable(value = "id") id: Long): SampleResponseDto?{
        return sampleService.deleteById(id)
    }

    @RequestMapping(method = [RequestMethod.POST])
    fun create(@RequestBody samplerRequestDto: SampleRequestDto): SampleResponseDto{
        return sampleService.create(samplerRequestDto)
    }

}
package com.easygoing.backend.services.sample.service

import com.easygoing.backend.services.sample.dto.SampleRequestDto
import com.easygoing.backend.services.sample.dto.SampleResponseDto

interface SampleService {
    fun listAll(): List<SampleResponseDto>
    fun getById(id: Long): SampleResponseDto?
    fun deleteById(id: Long): SampleResponseDto?
    fun create(samplerRequestDto: SampleRequestDto): SampleResponseDto
}
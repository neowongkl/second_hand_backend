package com.easygoing.backend.services.sample.service

import com.easygoing.backend.services.sample.dto.SampleMariaDbRequestDto
import com.easygoing.backend.services.sample.dto.SampleMariaDbResponseDto

interface SampleMariaDbService {
    fun listAll(): List<SampleMariaDbResponseDto>
    fun findById(id: Long): SampleMariaDbResponseDto?
    fun create(sampleMariaDbRequestDto: SampleMariaDbRequestDto): SampleMariaDbResponseDto
    fun deleteById(id: Long): SampleMariaDbResponseDto?
}
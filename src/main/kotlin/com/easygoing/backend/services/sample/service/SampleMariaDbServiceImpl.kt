package com.easygoing.backend.services.sample.service

import com.easygoing.backend.services.sample.coverter.SampleMariaDbConverter
import com.easygoing.backend.services.sample.dto.SampleMariaDbRequestDto
import com.easygoing.backend.services.sample.dto.SampleMariaDbResponseDto
import com.easygoing.backend.services.sample.respository.SampleMariaDbRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SampleMariaDbServiceImpl : SampleMariaDbService{

    @Autowired
    private lateinit var sampleMariaDbRepository: SampleMariaDbRepository

    @Autowired
    private lateinit var sampleMariaDbConverter: SampleMariaDbConverter

    override fun listAll(): List<SampleMariaDbResponseDto> {
        return sampleMariaDbRepository.findAll().let {
            sampleMariaDbConverter.daoListToResponseDtoList(it)
        }
    }

    override fun findById(id: Long): SampleMariaDbResponseDto? {
        return sampleMariaDbRepository.findByIdOrNull(id)?.let {
            sampleMariaDbConverter.daoToResponseDto(it)
        }
    }

    override fun create(sampleMariaDbRequestDto: SampleMariaDbRequestDto): SampleMariaDbResponseDto {
        return sampleMariaDbConverter.requestDtoToDao(sampleMariaDbRequestDto).let { _requestDto->
            sampleMariaDbRepository.save(_requestDto).let {
                sampleMariaDbConverter.daoToResponseDto(it)
            }
        }
    }

    override fun deleteById(id: Long): SampleMariaDbResponseDto? {
        return sampleMariaDbRepository.findByIdOrNull(id)?.let {
            sampleMariaDbRepository.deleteById(id)
            sampleMariaDbConverter.daoToResponseDto(it)
        }
    }
}
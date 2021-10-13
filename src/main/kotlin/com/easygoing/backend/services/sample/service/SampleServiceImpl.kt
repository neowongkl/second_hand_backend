package com.easygoing.backend.services.sample.service

import com.easygoing.backend.services.sample.coverter.SampleConverter
import com.easygoing.backend.services.sample.dao.SampleDao
import com.easygoing.backend.services.sample.dto.SampleRequestDto
import com.easygoing.backend.services.sample.dto.SampleResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SampleServiceImpl : SampleService {
    val sample1 = SampleDao(id = 1, sampleInt = 1, sampleBoolean = true, sampleString = "sample 1")
    val sample2 = SampleDao(id = 2, sampleInt = 2, sampleBoolean = false, sampleString = "sample 2")
    val sample3 = SampleDao(id = 3, sampleInt = 3, sampleBoolean = true, sampleString = "sample 3")
    val dummySampleList = mutableListOf(sample1, sample2, sample3)

    @Autowired
    private lateinit var sampleConverter: SampleConverter

    override fun listAll(): List<SampleResponseDto> {
        return sampleConverter.daoListToResponseDtoList(dummySampleList)
    }

    override fun getById(id: Long): SampleResponseDto? {
        return dummySampleList.firstOrNull{ it.id == id }?.let {
            sampleConverter.daoToResponseDto(it)
        }
    }

    override fun deleteById(id: Long): SampleResponseDto? {
        return dummySampleList.firstOrNull{ it.id == id }?.let {
            dummySampleList.remove(it)
            sampleConverter.daoToResponseDto(it)
        }
    }

    override fun create(samplerRequestDto: SampleRequestDto): SampleResponseDto {
        sampleConverter.requestDtoToDao(samplerRequestDto).run {
            dummySampleList.add(this)
        }
        return dummySampleList.last().let {
            sampleConverter.daoToResponseDto(it)
        }
    }
}
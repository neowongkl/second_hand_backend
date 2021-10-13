package com.easygoing.backend.services.sample.coverter

import com.easygoing.backend.services.sample.dao.SampleDao
import com.easygoing.backend.services.sample.dto.SampleRequestDto
import com.easygoing.backend.services.sample.dto.SampleResponseDto
import org.springframework.stereotype.Component

@Component
class SampleConverter {
    fun daoListToResponseDtoList(sources: List<SampleDao>): List<SampleResponseDto>{
        return sources.map { _source ->
            daoToResponseDto(_source)
        }
    }

    fun daoToResponseDto(source: SampleDao): SampleResponseDto{
        return SampleResponseDto(
            id = source.id,
            sampleInt = source.sampleInt,
            sampleBoolean = source.sampleBoolean,
            sampleString = source.sampleString + " via converter"
        )
    }

    fun requestDtoToDao(source: SampleRequestDto): SampleDao{
        return SampleDao(
            id = source.sampleInt!!.toLong(),
            sampleInt = source.sampleInt!!,
            sampleBoolean = source.sampleBoolean!!,
            sampleString = source.sampleString!!
        )
    }
}
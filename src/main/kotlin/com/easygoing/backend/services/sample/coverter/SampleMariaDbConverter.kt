package com.easygoing.backend.services.sample.coverter

import com.easygoing.backend.services.sample.dao.SampleMariaDb
import com.easygoing.backend.services.sample.dto.SampleMariaDbRequestDto
import com.easygoing.backend.services.sample.dto.SampleMariaDbResponseDto
import org.springframework.stereotype.Component

@Component
class SampleMariaDbConverter {
    fun requestDtoToDao(source: SampleMariaDbRequestDto): SampleMariaDb{
        return SampleMariaDb(sampleString = source.sampleString)
    }

    fun daoToResponseDto(source: SampleMariaDb): SampleMariaDbResponseDto{
        return SampleMariaDbResponseDto(
            id = source.id!!,
            sampleString = source.sampleString,
            createdBy = source.createdBy,
            createdOn = source.createdOn.toString(),
            lastModifiedBy = source.lastModifiedBy,
            lastModifiedDate = source.lastModifiedDate.toString()
        )
    }

    fun daoListToResponseDtoList(sources: List<SampleMariaDb>): List<SampleMariaDbResponseDto>{
        return sources.map { _source->
            daoToResponseDto(_source)
        }
    }
}
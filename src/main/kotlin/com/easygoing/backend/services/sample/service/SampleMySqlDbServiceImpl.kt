package com.easygoing.backend.services.sample.service

import com.easygoing.backend.services.sample.dao.SampleMySqlDb
import com.easygoing.backend.services.sample.respository.SampleMySqlDbRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SampleMySqlDbServiceImpl: SampleMySqlDbService {

    @Autowired
    private lateinit var sampleMySqlDbRepository: SampleMySqlDbRepository

    override fun listAll(): List<SampleMySqlDb> {
        return sampleMySqlDbRepository.findAll()
    }
}
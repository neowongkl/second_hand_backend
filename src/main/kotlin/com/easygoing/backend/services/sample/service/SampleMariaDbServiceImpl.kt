package com.easygoing.backend.services.sample.service

import com.easygoing.backend.services.sample.dao.SampleMariaDb
import com.easygoing.backend.services.sample.respository.SampleMariaDbRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SampleMariaDbServiceImpl : SampleMariaDbService{

    @Autowired
    private lateinit var sampleMariaDbRepository: SampleMariaDbRepository

    override fun listAll(): List<SampleMariaDb> {
        return sampleMariaDbRepository.findAll()
    }
}
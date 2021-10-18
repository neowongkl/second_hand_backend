package com.easygoing.backend.services.sample.service

import com.easygoing.backend.services.sample.dao.SampleMySqlDb

interface SampleMySqlDbService {
    fun listAll(): List<SampleMySqlDb>
}
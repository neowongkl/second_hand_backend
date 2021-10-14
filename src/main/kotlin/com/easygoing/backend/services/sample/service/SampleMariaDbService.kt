package com.easygoing.backend.services.sample.service

import com.easygoing.backend.services.sample.dao.SampleMariaDb

interface SampleMariaDbService {
    fun listAll(): List<SampleMariaDb>
}
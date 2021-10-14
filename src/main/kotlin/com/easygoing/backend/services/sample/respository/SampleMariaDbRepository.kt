package com.easygoing.backend.services.sample.respository

import com.easygoing.backend.services.sample.dao.SampleMariaDb
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SampleMariaDbRepository : JpaRepository<SampleMariaDb, Long> {
    
}
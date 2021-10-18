package com.easygoing.backend.services.sample.respository

import com.easygoing.backend.services.core.annotations.MySqlDataSource
import com.easygoing.backend.services.sample.dao.SampleMySqlDb
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
@MySqlDataSource
interface SampleMySqlDbRepository : JpaRepository<SampleMySqlDb,Long>{
}
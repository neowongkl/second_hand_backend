package com.easygoing.backend.services.user.repository

import com.easygoing.backend.services.core.annotations.MariaDbDataSource
import com.easygoing.backend.services.user.dao.AuthorityDao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
@MariaDbDataSource
interface AuthorityRepository : JpaRepository<AuthorityDao, Long> {
}
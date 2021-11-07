package com.easygoing.backend.services.user.repository

import com.easygoing.backend.services.core.annotations.MariaDbDataSource
import com.easygoing.backend.services.user.dao.UserDao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
@MariaDbDataSource
interface UserRepository: JpaRepository<UserDao, Long> {
    fun findByusername(userName: String) : UserDao?
    fun findByusernameOrEmail(username: String, email: String) : UserDao?
    fun existsByusername(userName: String): Boolean
    fun findByemail(email: String): UserDao?
    fun existsByemail(email: String): Boolean
}
package com.easygoing.backend.services.core.dao

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class AuditableEntity {

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    lateinit var createdBy : String

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_on", nullable = false, updatable = false)
    lateinit var createdOn : LocalDateTime

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false)
    lateinit var lastModifiedBy : String

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_modified_date", nullable = false)
    lateinit var lastModifiedDate: LocalDateTime
}
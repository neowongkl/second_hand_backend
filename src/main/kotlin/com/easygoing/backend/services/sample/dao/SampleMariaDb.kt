package com.easygoing.backend.services.sample.dao

import com.easygoing.backend.services.core.dao.AuditableEntity
import javax.persistence.*

@Entity
@Table(name = "sample_maria_db")
data class SampleMariaDb (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long? = null,

        @Column(name = "sample_string", nullable = false, length = 255)
        var sampleString : String
) : AuditableEntity()
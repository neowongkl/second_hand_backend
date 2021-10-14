package com.easygoing.backend.services.sample.dao

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "sample_maria_db")
data class SampleMariaDb (
        @Id
        var id : Long,

        @Column(name = "sample_string", nullable = false, length = 255)
        var sampleString : String
)
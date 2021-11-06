package com.easygoing.backend.services.sample.dao

import javax.persistence.*

@Entity
@Table(name = "sample_mysql_db")
data class SampleMySqlDb (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0,

    @Column(name = "my_string", nullable = false, length = 255)
    var myString : String
)

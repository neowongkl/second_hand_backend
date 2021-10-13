package com.easygoing.backend.services.sample.dao

data class SampleDao(
    var id : Long,
    var sampleInt : Int = 0,
    var sampleBoolean : Boolean = false,
    var sampleString : String = "this is sample dao"
)
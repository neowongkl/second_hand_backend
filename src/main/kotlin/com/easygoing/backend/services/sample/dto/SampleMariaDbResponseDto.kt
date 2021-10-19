package com.easygoing.backend.services.sample.dto

data class SampleMariaDbResponseDto (
    var id : Long,
    var sampleString : String,
    var createdBy: String,
    var createdOn: String,
    var lastModifiedBy: String,
    var lastModifiedDate: String,

)
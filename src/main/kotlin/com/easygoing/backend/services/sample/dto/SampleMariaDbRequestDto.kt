package com.easygoing.backend.services.sample.dto

import javax.validation.constraints.NotBlank

data class SampleMariaDbRequestDto (
    @field:NotBlank(message = "sample string should not blank")
    var sampleString : String
)
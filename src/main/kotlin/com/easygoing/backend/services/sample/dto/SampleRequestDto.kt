package com.easygoing.backend.services.sample.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class SampleRequestDto (
    @field:NotNull(message = "sample Int should not null")
    var sampleInt : Int,
    @field:NotNull(message = "sample Boolean should not null")
    var sampleBoolean : Boolean,
    @field:NotBlank(message = "sample String should not empty")
    var sampleString : String
)
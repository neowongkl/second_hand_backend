package com.easygoing.backend.services.product.dto

data class ProductResponseDto (
    var id : Long = 0,
    var name : String,
    var length : Double,
    var width : Double,
    var height : Double,
    var price : Double,
    var description : String,
    var category : String,
    var status : String
)
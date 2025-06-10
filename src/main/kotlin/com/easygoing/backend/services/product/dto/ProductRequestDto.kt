package com.easygoing.backend.services.product.dto

data class ProductRequestDto (
    var name : String,
    var length : Double,
    var width : Double,
    var height : Double,
    var price : Double,
    var description : String,
    var category : String,
    var status : String
)
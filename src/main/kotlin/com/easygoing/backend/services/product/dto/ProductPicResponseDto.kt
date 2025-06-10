package com.easygoing.backend.services.product.dto

data class ProductPicResponseDto (
    var id: Long = 0,
    var productId: Long,
    var fileType: String,
    var content: String,
)
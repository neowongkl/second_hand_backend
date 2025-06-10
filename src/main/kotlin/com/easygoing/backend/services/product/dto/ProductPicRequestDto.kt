package com.easygoing.backend.services.product.dto

data class ProductPicRequestDto(
    var productId: Long,
    var fileType: String,
    var content: String,
)
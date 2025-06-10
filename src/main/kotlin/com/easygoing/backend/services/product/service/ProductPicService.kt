package com.easygoing.backend.services.product.service

import com.easygoing.backend.services.product.dao.ProductPicDao
import com.easygoing.backend.services.product.dto.ProductPicRequestDto
import com.easygoing.backend.services.product.dto.ProductPicResponseDto

interface ProductPicService {
    //create
    fun create(productPicRequestDto: ProductPicRequestDto): ProductPicResponseDto

    //update

    //delete
}
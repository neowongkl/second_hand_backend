package com.easygoing.backend.services.product.service

import com.easygoing.backend.services.product.dto.ProductDetailResponseDto
import com.easygoing.backend.services.product.dto.ProductRequestDto
import com.easygoing.backend.services.product.dto.ProductResponseDto

interface ProductService {
    //create
    fun create(productRequestDto: ProductRequestDto): ProductResponseDto

    //update
    fun update(id: Long, productRequestDto: ProductRequestDto): ProductResponseDto

    //delete

    //get all
    fun getAll(): List<ProductResponseDto>

    //get by id
    fun getById(id: Long): ProductResponseDto?

    //get detail by Id
    fun getDetailById(id: Long): ProductDetailResponseDto

    //list all
    fun listAll(): List<ProductDetailResponseDto>

    //filter
}
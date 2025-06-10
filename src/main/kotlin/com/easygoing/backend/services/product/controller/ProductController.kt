package com.easygoing.backend.services.product.controller

import com.easygoing.backend.services.product.dto.ProductDetailResponseDto
import com.easygoing.backend.services.product.dto.ProductRequestDto
import com.easygoing.backend.services.product.dto.ProductResponseDto
import com.easygoing.backend.services.product.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["product"])
class ProductController {

    @Autowired
    private lateinit var productService: ProductService

    @RequestMapping(method = [RequestMethod.POST])
    fun create(@RequestBody productRequestDto: ProductRequestDto): ProductResponseDto {
        return productService.create(productRequestDto)
    }

    @RequestMapping(method = [RequestMethod.GET])
    fun getAll(): List<ProductResponseDto>{
        return productService.getAll()
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Long) : ProductResponseDto?{
        return productService.getById(id)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.POST])
    fun updateById(@PathVariable(value = "id") id: Long, @RequestBody productRequestDto: ProductRequestDto): ProductResponseDto{
        return productService.update(id, productRequestDto)
    }

    //product with pic
    @RequestMapping(value = ["/list"], method = [RequestMethod.GET])
    fun listAll() : List<ProductDetailResponseDto>{
        return productService.listAll()
    }

    @RequestMapping(value = ["/detail/{id}"], method = [RequestMethod.GET])
    fun getDetailById(@PathVariable(value = "id") id: Long) : ProductDetailResponseDto?{
        return productService.getDetailById(id)
    }
}
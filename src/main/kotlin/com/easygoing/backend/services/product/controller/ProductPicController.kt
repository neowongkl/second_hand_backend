package com.easygoing.backend.services.product.controller

import com.easygoing.backend.services.product.dto.ProductPicRequestDto
import com.easygoing.backend.services.product.dto.ProductPicResponseDto
import com.easygoing.backend.services.product.service.ProductPicService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = ["productpic"])
class ProductPicController {

    @Autowired
    private lateinit var productPicService: ProductPicService

    @RequestMapping(method = [RequestMethod.POST])
    fun create(@RequestBody productPicRequestDto: ProductPicRequestDto): ProductPicResponseDto {
        return productPicService.create(productPicRequestDto)
    }

//    @RequestMapping(method = [RequestMethod.GET])
//    fun listAll(): List<ProductResponseDto>{
//        return productService.listAll()
//    }
//
//    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
//    fun getById(@PathVariable(value = "id") id: Long) : ProductResponseDto?{
//        return productService.getById(id)
//    }
//
//    @RequestMapping(value = ["/{id}"], method = [RequestMethod.POST])
//    fun updateById(@PathVariable(value = "id") id: Long, @RequestBody productRequestDto: ProductRequestDto): ProductResponseDto {
//        return productService.update(id, productRequestDto)
//    }

}
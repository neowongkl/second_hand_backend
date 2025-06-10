package com.easygoing.backend.services.product.converter

import com.easygoing.backend.services.product.constant.ProductCategory
import com.easygoing.backend.services.product.constant.ProductStatus
import com.easygoing.backend.services.product.dao.ProductDao
import com.easygoing.backend.services.product.dao.ProductPicDao
import com.easygoing.backend.services.product.dto.ProductDetailResponseDto
import com.easygoing.backend.services.product.dto.ProductRequestDto
import com.easygoing.backend.services.product.dto.ProductResponseDto
import org.springframework.stereotype.Component
import java.util.*
import javax.persistence.Column
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Component
class ProductConverter {
    fun daoToResponseDto(source: ProductDao) : ProductResponseDto{
        return ProductResponseDto(
            id = source.id,
            name = source.name,
            length = source.length,
            width = source.width,
            height = source.height,
            price = source.price,
            description = source.description,
            category = source.category.category,
            status = source.status.status,
        )
    }

    fun daoListToResponseDtoList(source: List<ProductDao>): List<ProductResponseDto>{
        return source.map {
            daoToResponseDto(it)
        }
    }

    fun requestDtoToDao(source: ProductRequestDto): ProductDao{
        return ProductDao(
            name = source.name,
            length = source.length,
            width = source.width,
            height = source.height,
            price = source.price,
            description = source.description,
            category = ProductCategory.valueOf(source.category),
            status = ProductStatus.valueOf(source.status)
        )
    }

    fun updateDao(productDao: ProductDao, productRequestDto: ProductRequestDto){
        productDao.also {
            it.name = productRequestDto.name
            it.length = productRequestDto.length
            it.width = productRequestDto.width
            it.height = productRequestDto.height
            it.price = productRequestDto.price
            it.description = productRequestDto.description
            it.category = ProductCategory.valueOf(productRequestDto.category)
            it.status = ProductStatus.valueOf(productRequestDto.status)
        }
    }

    fun daoToProductDetailResponse(productDao: ProductDao, productPicDaoList: List<ProductPicDao>): ProductDetailResponseDto{
        return ProductDetailResponseDto(
            id = productDao.id,
            name = productDao.name,
            length = productDao.length,
            width = productDao.width,
            height = productDao.height,
            price = productDao.price,
            description = productDao.description,
            category = productDao.category.category,
            status = productDao.status.status,
            picList = productPicDaoList.map { Base64.getEncoder().encodeToString(it.content)}
        )
    }
}
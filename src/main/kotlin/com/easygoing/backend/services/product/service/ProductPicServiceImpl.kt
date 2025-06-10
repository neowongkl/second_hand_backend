package com.easygoing.backend.services.product.service

import com.easygoing.backend.services.product.dao.ProductPicDao
import com.easygoing.backend.services.product.dto.ProductPicRequestDto
import com.easygoing.backend.services.product.dto.ProductPicResponseDto
import com.easygoing.backend.services.product.repository.ProductPicRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductPicServiceImpl: ProductPicService {

    @Autowired
    private lateinit var productPicRepository: ProductPicRepository

    override fun create(productPicRequestDto: ProductPicRequestDto): ProductPicResponseDto {
        return ProductPicDao(
            productId = productPicRequestDto.productId,
            fileType = "jpeg",
            content = Base64.getDecoder().decode(productPicRequestDto.content)
        ).let {
            productPicRepository.save(it).let { _productDao->
                ProductPicResponseDto(
                    id = _productDao.id,
                    productId = _productDao.productId,
                    fileType = _productDao.fileType,
                    content = Base64.getEncoder().encodeToString(_productDao.content)
                )
            }
        }
    }

}
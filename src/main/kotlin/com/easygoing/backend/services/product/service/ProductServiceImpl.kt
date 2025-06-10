package com.easygoing.backend.services.product.service

import com.easygoing.backend.services.product.converter.ProductConverter
import com.easygoing.backend.services.product.dto.ProductDetailResponseDto
import com.easygoing.backend.services.product.dto.ProductRequestDto
import com.easygoing.backend.services.product.dto.ProductResponseDto
import com.easygoing.backend.services.product.repository.ProductPicRepository
import com.easygoing.backend.services.product.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProductServiceImpl: ProductService {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var productPicRepository: ProductPicRepository

    @Autowired
    private lateinit var productConverter: ProductConverter

    override fun create(productRequestDto: ProductRequestDto): ProductResponseDto {
        return productConverter.requestDtoToDao(productRequestDto).let { _product->
            productRepository.save(_product).let { _productDao->
                productConverter.daoToResponseDto(_productDao)
            }
        }
    }

    override fun update(id: Long, productRequestDto: ProductRequestDto): ProductResponseDto {
        return productRepository.findByIdOrNull(id)?.let { _productDao->
            productConverter.updateDao(_productDao, productRequestDto)
            productRepository.save(_productDao).let { _updatedDao->
                productConverter.daoToResponseDto(_updatedDao)
            }
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "product with ID: $id not found")
    }

    override fun getAll(): List<ProductResponseDto> {
        return productRepository.findAll().let { _productDaoList->
            productConverter.daoListToResponseDtoList(_productDaoList)
        }
    }

    override fun getById(id: Long): ProductResponseDto? {
        return productRepository.findByIdOrNull(id)?.let { _productDao->
            productConverter.daoToResponseDto(_productDao)
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "product with ID: $id not found")
    }

    override fun getDetailById(id: Long): ProductDetailResponseDto {
        return productRepository.findByIdOrNull(id)?.let { _productDao->
            productPicRepository.findByProductId(id)?.let { _productPicList->
                productConverter.daoToProductDetailResponse(_productDao, _productPicList)
            } ?:  throw ResponseStatusException(HttpStatus.NOT_FOUND, "picture  with product id: $id not found")
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "product with ID: $id not found")
    }

    override fun listAll(): List<ProductDetailResponseDto> {
        val result = mutableListOf<ProductDetailResponseDto>()
        productRepository.findAll().let { _productDaoList->
            _productDaoList.forEach{ _productDao->
                productPicRepository.findByProductIdAndIsProfile(_productDao.id, true)?.let { _productPic->
                    productConverter.daoToProductDetailResponse(_productDao, listOf(_productPic) ).let {
                        result.add(it)
                    }
                } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "picture  with product id: ${_productDao.id} not found")
            }
        }
        return result
    }
}
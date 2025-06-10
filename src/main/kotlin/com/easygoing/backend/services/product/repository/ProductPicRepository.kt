package com.easygoing.backend.services.product.repository

import com.easygoing.backend.services.core.annotations.MariaDbDataSource
import com.easygoing.backend.services.product.dao.ProductPicDao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
@MariaDbDataSource
interface ProductPicRepository: JpaRepository<ProductPicDao, Long> {
    fun findByProductId(productId: Long): List<ProductPicDao>?
    fun findByProductIdAndIsProfile(productId: Long, isProfile: Boolean): ProductPicDao?
}
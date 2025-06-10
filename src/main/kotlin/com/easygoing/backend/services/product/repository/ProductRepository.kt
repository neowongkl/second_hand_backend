package com.easygoing.backend.services.product.repository

import com.easygoing.backend.services.core.annotations.MariaDbDataSource
import com.easygoing.backend.services.product.dao.ProductDao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
@MariaDbDataSource
interface ProductRepository : JpaRepository<ProductDao, Long>{
}
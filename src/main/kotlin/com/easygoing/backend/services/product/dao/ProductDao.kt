package com.easygoing.backend.services.product.dao

import com.easygoing.backend.services.core.dao.AuditableEntity
import com.easygoing.backend.services.product.constant.ProductCategory
import com.easygoing.backend.services.product.constant.ProductStatus
import javax.persistence.*

@Entity
@Table(name = "product")
data class ProductDao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0,

    @Column(name = "name", nullable = false, length = 255)
    var name : String,

    @Column(name = "length", nullable = false, precision = 5, scale = 2)
    var length : Double,

    @Column(name = "width", nullable = false, precision = 5, scale = 2)
    var width : Double,

    @Column(name = "height", nullable = false, precision = 5, scale = 2)
    var height : Double,

    @Column(name = "price", nullable = false, precision = 5, scale = 2)
    var price : Double,

    @Column(name = "description", nullable = false, length = 255)
    var description : String,

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 20)
    var category : ProductCategory,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    var status : ProductStatus
) : AuditableEntity()
package com.easygoing.backend.services.product.dao

import com.easygoing.backend.services.core.dao.AuditableEntity
import javax.persistence.*

@Entity
@Table(name = "product_pic")
data class ProductPicDao (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "productid", nullable = false)
    var productId: Long,

    @Column(name = "file_type", nullable = false)
    var fileType: String,

    @Lob
    @Column(name = "content", nullable = false)
    var content: ByteArray,

    @Column(name = "is_profile")
    var isProfile : Boolean = false,
): AuditableEntity()
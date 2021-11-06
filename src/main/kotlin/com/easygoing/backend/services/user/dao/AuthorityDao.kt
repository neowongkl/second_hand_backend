package com.easygoing.backend.services.user.dao

import com.easygoing.backend.services.core.dao.AuditableEntity
import javax.persistence.*

@Entity
@Table(name = "authorities")
data class AuthorityDao (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long = 0,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "userid", referencedColumnName = "id")
        var user: UserDao? = null,

        @Column(name = "authority", nullable = false, length = 50)
        val authority : String,
): AuditableEntity()
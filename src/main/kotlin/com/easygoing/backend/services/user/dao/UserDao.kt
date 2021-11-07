package com.easygoing.backend.services.user.dao

import com.easygoing.backend.services.core.dao.AuditableEntity
import com.easygoing.backend.services.user.constant.AuthProvider
import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "users",
    uniqueConstraints = [
        UniqueConstraint(name = "username", columnNames = ["username"]),
        UniqueConstraint(name = "email", columnNames = ["email"])
    ]
)
data class UserDao (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0,

    @Column(name = "username", length = 50, unique = true, nullable = false)
    var username: String,

    @Column(name= "password", length = 100, nullable = false)
    var password: String,

    @Column(name= "enable")
    var enable: Boolean,

    @Email
    @Column(name = "email", length = 255, unique = true, nullable = false)
    val email: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", length = 15, nullable = false)
    val authProvider: AuthProvider,

    @Column(name = "provider_id", length = 50, nullable = false)
    val providerId: String,


): AuditableEntity(){
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    var authorities = mutableListOf<AuthorityDao>()
}
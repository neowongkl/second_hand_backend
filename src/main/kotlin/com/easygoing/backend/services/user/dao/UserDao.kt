package com.easygoing.backend.services.user.dao

import com.easygoing.backend.services.core.dao.AuditableEntity
import javax.persistence.*

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(name = "username", columnNames = ["username"])])
data class UserDao (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0,

    @Column(name = "username", length = 50, unique = true, nullable = false)
    var username: String,

    @Column(name= "password", length = 50, nullable = false)
    var password: String,

    @Column(name= "enable")
    var enable: Boolean,

): AuditableEntity(){
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    var authorities = mutableListOf<AuthorityDao>()
}
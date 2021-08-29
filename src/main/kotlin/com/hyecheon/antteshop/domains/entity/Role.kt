package com.hyecheon.antteshop.domains.entity

import com.hyecheon.antteshop.entity.BaseWithUserEntity
import lombok.*
import javax.persistence.*

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(length = 40, nullable = false, unique = true)
    var name: String? = null,

    @Column(length = 150, nullable = false)
    var description: String? = null,
) : BaseWithUserEntity()
package com.hyecheon.antteshop.entity

import lombok.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/21
 */
@Entity
@Builder
class UserFileInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var originalFileName: String? = null,
    var type: String? = null,
    var path: String? = null,
    var size: String? = null,
) : BaseWithUserEntity()
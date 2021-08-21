package com.hyecheon.antteshop.dto

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */

data class RoleDto(
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
) {
    constructor(id: Long) : this(id = id, null, null)
}
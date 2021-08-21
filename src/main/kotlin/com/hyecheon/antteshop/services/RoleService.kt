package com.hyecheon.antteshop.services

import com.hyecheon.antteshop.dto.RoleDto

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/15
 */
interface RoleService {
    fun findAll(): List<RoleDto>
}
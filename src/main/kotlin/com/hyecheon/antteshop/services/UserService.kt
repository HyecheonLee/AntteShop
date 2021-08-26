package com.hyecheon.antteshop.services


import com.hyecheon.antteshop.web.dto.UserDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
interface UserService {
    fun usersAll(): List<UserDto>
    fun users(pageable: Pageable): Page<UserDto>
    fun users(pageable: Pageable, keyword: String? = null): Page<UserDto>
    fun save(userDto: UserDto): UserDto
    fun save(id: Long, userDto: UserDto): UserDto
    fun existsByEmail(email: String): Boolean
    fun findById(id: Long): UserDto
    fun deleteById(id: Long)

}
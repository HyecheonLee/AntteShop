package com.hyecheon.antteshop.repositories

import com.hyecheon.antteshop.entity.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
}
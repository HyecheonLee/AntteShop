package com.hyecheon.antteshop.repositories

import com.hyecheon.antteshop.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean

    @Query("select u from User u where u.email like %:keyword% or u.firstName like %:keyword% or u.lastName like %:keyword%")
    fun findAll(keyword: String, pageable: Pageable): Page<User>
}
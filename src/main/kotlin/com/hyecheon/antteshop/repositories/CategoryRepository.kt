package com.hyecheon.antteshop.repositories

import com.hyecheon.antteshop.domains.entity.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/09/27
 */
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findAllByLevel(level: Int): List<Category>
    fun findAllByLevel(level: Int, pageable: Pageable): Page<Category>
}
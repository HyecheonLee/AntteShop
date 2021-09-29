package com.hyecheon.antteshop.services

import com.hyecheon.antteshop.domains.entity.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/09/29
 */
interface CategoryService {
    fun findAll(pageable: Pageable): Page<Category>
    fun findAllCategorySorted(): List<Category>
    fun save(category: Category): Category
}
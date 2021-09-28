package com.hyecheon.antteshop.services.impl

import com.hyecheon.antteshop.domains.entity.Category
import com.hyecheon.antteshop.repositories.CategoryRepository
import com.hyecheon.antteshop.services.CategoryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryService {
    override fun findAll(pageable: Pageable): Page<Category> {
        return categoryRepository.findAll(pageable)
    }

    override fun findAllCategorySorted(): List<Category> = run {
        val result = mutableListOf<Category>()
        val categories = categoryRepository.findAllByLevel(0)
        categories.forEach { subSearch(it, result) }
        result
    }

    private fun subSearch(category: Category, acc: MutableList<Category>) {
        acc.add(category)
        category.child.forEach { subSearch(it, acc) }
    }
}
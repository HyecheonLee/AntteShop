package com.hyecheon.antteshop.services.impl

import com.hyecheon.antteshop.domains.entity.Category
import com.hyecheon.antteshop.repositories.CategoryRepository
import com.hyecheon.antteshop.services.CategoryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Service
@Transactional
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryService {
    override fun findAll(pageable: Pageable): Page<Category> {
        val findAll = categoryRepository.findAll(pageable)
        return findAll
    }

    override fun findAllCategorySorted(pageable: Pageable): List<Category> = run {
        val result = mutableListOf<Category>()
        val categories = categoryRepository.findAllByLevel(0, pageable)
        categories.forEach { subSearch(it, result) }
        result
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

    override fun save(category: Category) = run {
        category.parent?.id?.let {
            val parentCategory = categoryRepository.findById(it).orElseThrow { RuntimeException("") }
            parentCategory.addChild(category)
            category.parent = parentCategory
            category.level = parentCategory.level + 1
        }
        categoryRepository.save(category)
    }
}
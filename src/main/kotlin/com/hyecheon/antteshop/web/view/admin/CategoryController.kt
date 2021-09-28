package com.hyecheon.antteshop.web.view.admin

import com.hyecheon.antteshop.domains.entity.Category
import com.hyecheon.antteshop.services.CategoryService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/09/29
 */
@Controller
@RequestMapping("/admin/categories")
class CategoryController(
    private val categoryService: CategoryService,
) {
    @GetMapping()
    fun getAll(
        model: Model,
        @PageableDefault(page = 0, size = 5, direction = Sort.Direction.DESC, sort = ["id"]) pageable: Pageable,
    ): String {
        val pageCategory = categoryService.findAll(pageable)
        model.addAttribute("categories", pageCategory.content)
        return "admin/categories/index"
    }

    @GetMapping("/new")
    fun newCategory(@ModelAttribute("category") category: Category, model: Model): String {
        val categories = categoryService.findAllCategorySorted()
        model.addAttribute("categories", categories)
        return "admin/categories/new"
    }
}
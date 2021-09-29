package com.hyecheon.antteshop.web.view.admin

import com.hyecheon.antteshop.domains.entity.Category
import com.hyecheon.antteshop.services.CategoryService
import com.hyecheon.antteshop.utils.saveFile
import org.springframework.core.io.UrlResource
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes

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

    @ResponseBody
    @GetMapping("/image/{id}/{image}")
    fun categoryImage(@PathVariable id: Long, @PathVariable image: String) = run {
        val urlResource = UrlResource("file:category-images/${id}/${image}")
        ResponseEntity.ok(urlResource)
    }

    @PostMapping("/new")
    fun newCategory(
        category: Category,
        fileImage: MultipartFile?,
        redirectAttributes: RedirectAttributes,
    ) = run {
        val fileName = fileImage?.originalFilename?.let { StringUtils.cleanPath(it) } ?: ""

        category.image = fileName
        val savedCategory = categoryService.save(category)
        val uploadDir = "category-images/${savedCategory.id}"
        fileImage?.saveFile(uploadDir = uploadDir, fileName = fileName)
        redirectAttributes.addFlashAttribute("message", "카테고리 저장에 성공했습니다.")
        "redirect:/admin/categories"
    }
}
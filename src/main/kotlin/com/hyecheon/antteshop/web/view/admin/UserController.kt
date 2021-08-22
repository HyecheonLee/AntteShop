package com.hyecheon.antteshop.web.view.admin

import com.hyecheon.antteshop.domains.PageInfo
import com.hyecheon.antteshop.mapper.UserMapper
import com.hyecheon.antteshop.services.RoleService
import com.hyecheon.antteshop.services.UserService
import com.hyecheon.antteshop.web.dto.UserCreateDto
import com.hyecheon.antteshop.web.dto.UserDto
import com.hyecheon.antteshop.web.dto.UserUpdateDto
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
@Controller
@RequestMapping("/admin/users")
class UserController(
    private val userService: UserService,
    private val roleService: RoleService,
) {

    @GetMapping("")
    fun users(
        @PageableDefault(page = 0, size = 5, direction = Sort.Direction.DESC, sort = ["id"]) pageable: Pageable,
        model: Model,
    ): String {
        val pageUser = userService.users(pageable)
        model.addAttribute("pageInfo", PageInfo.create(pageUser))
        val users = pageUser.content
        model.addAttribute("users", users)
        pageUser.totalPages
        if (!model.containsAttribute("message") ||
            StringUtils.hasText(model.getAttribute("message").toString())
        ) {
            model.addAttribute("message", null)
        }
        return "admin/users/index"
    }

    @GetMapping("/{id}/update")
    fun update(model: Model, @PathVariable id: Long): String {
        val roles = roleService.findAll()
        model.addAttribute("roles", roles)

        val userDto = userService.findById(id)
        model.addAttribute("user", userDto)
        return "admin/users/update"
    }

    @PostMapping("/{id}/update")
    fun update(
        @PathVariable id: Long,
        @ModelAttribute("user") userUpdateDto: UserUpdateDto,
        bindingResult: BindingResult,
        model: Model,
    ): String {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/users/${id}/update"
        } else {
            val uploadDir = "user-photos/${id}"
            userUpdateDto.saveImage(uploadDir)
            val userDto = UserMapper.INSTANCE.toDto(userUpdateDto)
            userService.save(id, userDto)
            return "redirect:/admin/users"
        }
    }

    @GetMapping("/new")
    fun new(model: Model): String {
        val user = UserCreateDto()
        model.addAttribute("user", user)
        val roles = roleService.findAll()
        model.addAttribute("roles", roles)
        return "admin/users/new"
    }

    @PostMapping("/new")
    fun new(
        @ModelAttribute("user") @Validated userCreateDto: UserCreateDto,
        bindingResult: BindingResult, model: Model,
    ): String {
        when {
            bindingResult.hasErrors() -> {
                val roles = roleService.findAll()
                model.addAttribute("roles", roles)
                return "admin/users/new"
            }
            userService.existsByEmail(userCreateDto.email!!) -> {
                bindingResult.addError(FieldError("user", "email", "이메일이 중복입니다."))
                val roles = roleService.findAll()
                model.addAttribute("roles", roles)
                return "admin/users/new"
            }
            else -> {
                val userDto = userCreateDto.toDto()
                userService.save(userDto)
                return "redirect:/admin/users"
            }
        }
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: Long, redirectAttributes: RedirectAttributes) = run {
        try {
            userService.deleteById(id)
            redirectAttributes.addFlashAttribute("message", "사용자 ID ${id}이(가) 성공적으로 삭제되었습니다.")
        } catch (e: UsernameNotFoundException) {
            redirectAttributes.addFlashAttribute("message", e.message)
        }
        "redirect:/admin/users"
    }

    @GetMapping("/{id}/enabled")
    fun enableUser(
        @PathVariable id: Long,
        @RequestParam status: Boolean,
        redirectAttributes: RedirectAttributes,
    ): String {
        var message: String?
        try {
            val userDto = UserDto()
            userDto.enabled = status
            userService.save(id, userDto)
            message = if (status) "사용자 ID ${id}이(가) 활성화되었습니다." else "사용자 ID ${id}이(가) 비활성화되었습니다."
        } catch (e: UsernameNotFoundException) {
            message = e.message
        }
        redirectAttributes.addFlashAttribute("message", message)
        return "redirect:/admin/users"
    }
}
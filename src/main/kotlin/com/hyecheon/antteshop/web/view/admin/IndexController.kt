package com.hyecheon.antteshop.web.view.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
@Controller
@RequestMapping("/admin")
class IndexController {
    @GetMapping("")
    fun indexPage() = run {
        "admin/index"
    }
}
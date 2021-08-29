package com.hyecheon.antteshop.web.view

import com.hyecheon.antteshop.annotation.BasicModel
import com.hyecheon.antteshop.annotation.Timer
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/13
 */
@Controller
class HomeController {
    @Timer
    @GetMapping("")
    fun homePage(model: Model): String {
        return "index"
    }

    @GetMapping("/login")
    fun loginPage() = run {
        "login"
    }
}
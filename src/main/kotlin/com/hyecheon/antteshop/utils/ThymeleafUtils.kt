package com.hyecheon.antteshop.utils

import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/23
 */

@Component
class ThymeleafUtils(
    private val httpServletRequest: HttpServletRequest,
) {

    fun isAsc(): Boolean = run {
        val sortParam = httpServletRequest.getParameter("sort") ?: ""
        return sortParam.contains("asc")
    }

    fun isDesc(): Boolean = run {
        return !isAsc()
    }

    fun sort() = run {
        if (isAsc()) "asc"
        else "desc"
    }

    fun sortField(field: String) = run {
        val sortParam = httpServletRequest.getParameter("sort") ?: ""
        sortParam.contains(field)
    }

    fun reverseSort() = run {
        val sortParam = httpServletRequest.getParameter("sort") ?: ""
        if (sortParam.contains("(asc|desc)".toRegex())) {
            if (isAsc()) {
                sortParam.replace("asc", "desc")
            } else {
                sortParam.replace("desc", "asc")
            }
        } else {
            "${sortParam},desc"
        }
    }
}
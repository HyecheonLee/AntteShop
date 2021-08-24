package com.hyecheon.antteshop.utils

import org.springframework.stereotype.Component
import org.springframework.web.util.UriUtils
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


    fun pageUri() = "${httpServletRequest.requestURI}${pageParameter(0)}"

    fun pageUri(page: Int = 0, _keyword: String? = null, _size: String? = null, _sort: String? = null) =
        "${httpServletRequest.requestURI}${pageParameter(page, _keyword, _size, _sort)}"

    fun pageParameter(page: Int = 0) = run {
        makeParameter(page,
            httpServletRequest.getParameter("size") ?: "",
            httpServletRequest.getParameter("sort") ?: "",
            httpServletRequest.getParameter("keyword") ?: "")
    }

    fun pageParameter(page: Int = 0, _keyword: String? = null, _size: String? = null, _sort: String? = null) = run {
        makeParameter(page,
            _size ?: httpServletRequest.getParameter("size") ?: "",
            _sort ?: httpServletRequest.getParameter("sort") ?: "",
            _keyword ?: httpServletRequest.getParameter("keyword") ?: ""
        )
    }

    private fun makeParameter(
        page: Int,
        size: String,
        sort: String,
        keyword: String,
    ): String {
        val pageParameter = StringBuilder("?page=${page}")
        if (size.isNotEmpty()) {
            pageParameter.append("&").append("size=$size")
        }
        if (sort.isNotEmpty()) {
            pageParameter.append("&").append("sort=$sort")
        }
        if (keyword.isNotEmpty()) {
            pageParameter.append("&").append("keyword=$keyword")
        }
        return UriUtils.encodeQuery(pageParameter.toString(), "UTF-8")
    }
}
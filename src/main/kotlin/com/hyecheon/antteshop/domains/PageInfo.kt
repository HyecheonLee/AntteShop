package com.hyecheon.antteshop.domains

import org.springframework.data.domain.Page

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
data class PageInfo(
    val isNext: Boolean,
    val isPrev: Boolean,
    val current: Int,
    val totalPages: Int,
    val pageList: List<Int>,
    val prePageNum: Int = 0,
    val nexPageNum: Int = 0,
) {

    companion object {
        fun create(page: Page<*>?, showPage: Int = 2): PageInfo? {
            if (page == null) return null
            val pageList = when {
                page.isFirst -> 0..(showPage * 2)
                page.isLast -> ((page.totalPages - showPage * 2) - 1)..(page.totalPages - 1)
                else -> {
                    val startIndex = page.number - showPage
                    val endIndex = page.number + showPage
                    when {
                        startIndex <= 0 -> 0..showPage * 2
                        endIndex >= page.totalPages -> ((page.totalPages - showPage * 2) - 1)..(page.totalPages - 1)
                        else -> startIndex..endIndex
                    }
                }
            }.toList()
            val prePageNum = (pageList.first() - showPage - 1).coerceAtLeast(0)
            val nexPageNum = (pageList.last() + showPage + 1).coerceAtMost(page.totalPages - 1)
            return PageInfo(page.hasNext(),
                page.hasPrevious(),
                page.number,
                page.totalPages,
                pageList,
                prePageNum,
                nexPageNum)
        }
    }
}
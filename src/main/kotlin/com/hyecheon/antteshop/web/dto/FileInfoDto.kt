package com.hyecheon.antteshop.web.dto

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/21
 */
data class FileInfoDto(
    var id: Long? = null,
    var originalFileName: String? = null,
    var type: String? = null,
    var path: String? = null,
    var size: Long? = null,
)
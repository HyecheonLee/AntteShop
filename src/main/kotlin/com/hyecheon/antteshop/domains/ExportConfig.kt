package com.hyecheon.antteshop.domains

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/26
 */
data class ExportData<T>(
    val header: List<String>,
    val field: List<String>,
    val data: List<T>,
    val fileName: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")),
    val type: String? = null,
    val template: String? = null,
)
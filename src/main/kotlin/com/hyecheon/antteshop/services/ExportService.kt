package com.hyecheon.antteshop.services

import com.hyecheon.antteshop.domains.ExportData
import com.hyecheon.antteshop.entity.User
import java.io.Writer
import java.text.SimpleDateFormat
import java.util.*

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/26
 */
interface ExportService {
    fun <T> export(exportData: ExportData<T>, writer: Writer)
}
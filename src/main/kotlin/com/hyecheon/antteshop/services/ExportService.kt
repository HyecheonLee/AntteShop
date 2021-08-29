package com.hyecheon.antteshop.services

import com.hyecheon.antteshop.domains.ExportData

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/26
 */
interface ExportService {
    fun <T> export(exportData: ExportData<T>): String
}
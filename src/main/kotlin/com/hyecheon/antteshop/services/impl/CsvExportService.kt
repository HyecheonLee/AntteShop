package com.hyecheon.antteshop.services.impl

import com.hyecheon.antteshop.domains.ExportData
import com.hyecheon.antteshop.entity.User
import com.hyecheon.antteshop.services.ExportService
import org.springframework.stereotype.Service
import org.supercsv.io.CsvBeanWriter
import org.supercsv.prefs.CsvPreference
import java.io.Writer
import java.text.SimpleDateFormat
import java.util.*

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/26
 */
@Service
class CsvExportService : ExportService {
    override fun <T> export(exportData: ExportData<T>, writer: Writer) {
        CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE).use {
            it.writeHeader(*exportData.header.toTypedArray())
            exportData.data.forEach { t ->
                it.write(t, *exportData.field.toTypedArray())
            }
        }
    }
}
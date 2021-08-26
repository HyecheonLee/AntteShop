package com.hyecheon.antteshop.services.impl

import com.hyecheon.antteshop.domains.ExportData
import com.hyecheon.antteshop.services.ExportService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.supercsv.io.CsvBeanWriter
import org.supercsv.prefs.CsvPreference
import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.util.*

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/26
 */
@Service
class CsvExportService(
    @Value("\${temp.file.dir:temp/}") val tempDir: String,
) : ExportService {
    override fun <T> export(exportData: ExportData<T>): String {
        val file = File("${tempDir}${UUID.randomUUID()}")
        val output = FileWriter(file)
        output.use {
            CsvBeanWriter(output, CsvPreference.STANDARD_PREFERENCE).use {
                it.writeHeader(*exportData.header.toTypedArray())
                exportData.data.forEach { t ->
                    it.write(t, *exportData.field.toTypedArray())
                }
                it.flush()
            }
        }
        return file.absoluteFile.toString()
    }
}
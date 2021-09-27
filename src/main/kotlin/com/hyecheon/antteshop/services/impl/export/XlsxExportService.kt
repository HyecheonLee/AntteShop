package com.hyecheon.antteshop.services.impl.export

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.hyecheon.antteshop.domains.ExportData
import com.hyecheon.antteshop.services.ExportService
import lombok.extern.log4j.Log4j2
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream
import java.util.*

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/26
 */
@Service
class XlsxExportService(
    @Value("\${temp.file.dir:temp/}") val tempDir: String,
    val objectMapper: ObjectMapper,
) : ExportService {
    private val log = LoggerFactory.getLogger(this::class.java)
    override fun <T> export(exportData: ExportData<T>): String {
        val file = File("${tempDir}${UUID.randomUUID()}")
        FileOutputStream(file).use { output ->
            XSSFWorkbook().use { workBook ->
                val sheet = headerLine(workBook, exportData.header)
                dateLine(sheet, exportData)
                workBook.write(output)
            }
        }
        return file.absolutePath.toString()
    }

    private fun headerLine(workbook: XSSFWorkbook, header: List<String>) = run {
        val sheet = workbook.createSheet()
        val row = sheet.createRow(0)
        header.forEachIndexed { index, s ->
            createCell(row, index, s)
        }
        sheet
    }

    private fun <T> dateLine(sheet: XSSFSheet, exportData: ExportData<T>) = run {
        exportData.data.forEachIndexed { index, t: T ->
            val row = sheet.createRow(index + 1)
            val props = objectMapper.convertValue(t, object : TypeReference<Map<String, Any>>() {})
            exportData.field.forEachIndexed { index, s ->
                createCell(row, index, props[s] ?: "")
            }
        }
    }

    private fun createCell(row: XSSFRow, columnIndex: Int, value: Any, style: CellStyle? = null) = run {
        val cell = row.createCell(columnIndex)
        when (value) {
            is Number -> {
                cell.setCellValue(value.toDouble())
            }
            is Boolean -> {
                cell.setCellValue(value)
            }
            else -> {
                cell.setCellValue(value.toString())
            }
        }
        style?.apply { cell.setCellStyle(this) }
    }
}
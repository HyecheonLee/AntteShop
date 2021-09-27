package com.hyecheon.antteshop.services.impl.export

import com.hyecheon.antteshop.domains.ExportData
import com.hyecheon.antteshop.services.ExportService
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset
import java.util.*


/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/26
 */
@Service
class PdfExportService(
    @Value("\${temp.file.dir:temp/}") val tempDir: String,
    val templateEngine: SpringTemplateEngine,
) : ExportService {
    override fun <T> export(exportData: ExportData<T>): String {
        val html = toHtml(exportData) ?: throw RuntimeException("parse html error")
        val document = Jsoup.parse(html)
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml)
        document.charset(Charset.defaultCharset())

        val renderer = ITextRenderer()
//        renderer.fontResolver.addFont("pdf/Code39.ttf", IDENTITY_H, EMBEDDED);
        renderer.setDocumentFromString(document.html())

        renderer.layout()

        val file = File("${tempDir}${UUID.randomUUID()}")
        FileOutputStream(file).use { output -> renderer.createPDF(output) }
        return file.absoluteFile.toString()
    }

    fun <T> toHtml(exportData: ExportData<T>): String? {
        if (exportData.template == "list-of-users") {
            return toHtmlListOfUsers(exportData)
        }
        return null
    }

    fun <T> toHtmlListOfUsers(exportData: ExportData<T>): String = run {
        val context = Context()
        context.setVariable("users", exportData.data)
        templateEngine.process("pdf/${exportData.template}", context)
    }
}
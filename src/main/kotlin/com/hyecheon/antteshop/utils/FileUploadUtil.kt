package com.hyecheon.antteshop.utils

import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/19
 */
fun MultipartFile.saveFile(uploadDir: String, fileName: String): String {
    val uploadPath = Paths.get(uploadDir)
    if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath)
    try {
        return this.inputStream.use {
            val filePath = uploadPath.resolve(fileName)
            Files.copy(it, filePath, StandardCopyOption.REPLACE_EXISTING)
            filePath.toString()
        }
    } catch (e: IOException) {
        throw IOException("파일을 저장할 수 없습니다: $fileName", e)
    }
}
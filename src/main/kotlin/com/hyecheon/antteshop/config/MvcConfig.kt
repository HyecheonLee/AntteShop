package com.hyecheon.antteshop.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.file.Paths

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/21
 */
@Configuration
class MvcConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        addResourceHandler(registry, "user-photos")
        addResourceHandler(registry, "category-images")
    }

    fun addResourceHandler(registry: ResourceHandlerRegistry, dirName: String) = run {
        val dir = Paths.get(dirName)
        val absolutePath = dir.toFile().absoluteFile
        registry.addResourceHandler("/${dirName}/**")
            .addResourceLocations("file:///$absolutePath/")
    }
}
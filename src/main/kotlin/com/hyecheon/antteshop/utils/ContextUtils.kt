package com.hyecheon.antteshop.utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/16
 */
@Component
class ContextUtils : ApplicationContextAware {

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

    companion object {
        private lateinit var context: ApplicationContext

        @kotlin.jvm.JvmStatic
        fun <T> getBean(clazz: Class<T>): T? {
            return context.getBean(clazz)
        }

        fun jsonString(any: Any) = run {
            val objectMapper = if (::context.isInitialized)
                context.getBean(ObjectMapper::class.java)
            else ObjectMapper()
            objectMapper.writeValueAsString(any)
        }
    }
}

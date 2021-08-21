package com.hyecheon.antteshop.utils

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
    }
}
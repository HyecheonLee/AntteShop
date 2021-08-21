package com.hyecheon.antteshop.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/21
 */
@EnableJpaAuditing
@Configuration
class JpaConfig {
    @Bean
    fun auditorProvider(): AuditorAware<String> {
        return AuditorAware { Optional.of("사용자이름..") }
    }
}
package com.hyecheon.antteshop.domains

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.hyecheon.antteshop.web.dto.UserDto
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaType

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/27
 */
internal class ExportDataTest {

    @DisplayName("1. 리플렉션 테스트")
    @Test
    internal fun test1() {
        val objectMapper = ObjectMapper()
        val userDto = UserDto(1, "hello@world.com", "", "")
        val props = objectMapper.convertValue(userDto, object : TypeReference<Map<String, Any>>() {})
        Assertions.assertThat(props["id"]).isEqualTo(1L)
        Assertions.assertThat(props["email"]).isEqualTo("hello@world.com")

    }

    inline fun <reified T : Any> T.asMap(): Map<String, Any?> {
        val props = T::class.memberProperties.associateBy { it.name }
        return props.keys.associateWith { props[it]?.get(this) }
    }
}
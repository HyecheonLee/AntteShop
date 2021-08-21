package com.hyecheon.antteshop.web.dto

import com.hyecheon.antteshop.mapper.UserMapper
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/16
 */

data class UserCreateDto(
    @field:Email @field:NotBlank
    var email: String? = null,
    @field:Size(min = 4, max = 20)
    var password: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var enabled: Boolean? = null,
    @field:NotEmpty
    var roles: MutableSet<Long>? = HashSet(),
) {
    fun toDto(): UserDto {
        return UserMapper.INSTANCE.toDto(this)
    }
}
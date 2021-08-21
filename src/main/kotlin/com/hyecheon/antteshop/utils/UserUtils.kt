package com.hyecheon.antteshop.utils

import com.hyecheon.antteshop.entity.User
import com.hyecheon.antteshop.mapper.UserMapper
import com.hyecheon.antteshop.web.dto.UserDto

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/16
 */
fun User.update(userDto: UserDto) {
    UserMapper.INSTANCE.updateUser(userDto, this)
}

fun User.toDto(): UserDto {
    return UserMapper.INSTANCE.toDto(this)
}

package com.hyecheon.antteshop.web.dto

import com.hyecheon.antteshop.entity.User
import com.hyecheon.antteshop.mapper.UserMapper
import com.hyecheon.antteshop.web.dto.RoleDto
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.thymeleaf.util.StringUtils
import java.util.stream.Collectors

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
data class UserDto(
    var id: Long? = null,
    var email: String? = null,
    var password: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var photos: String? = null,
    var enabled: Boolean? = null,
    var roles: MutableSet<RoleDto>? = null,
) {
    fun addRole(vararg roles: RoleDto?) {
        for (role in roles) {
            addRole(role)
        }
    }

    fun addRole(role: RoleDto?) {
        if (roles == null) roles = HashSet()
        if (role != null) roles!!.add(role)
    }

    fun displayRole(): String {
        return roles!!.stream().map(RoleDto::name).collect(Collectors.joining(",", "[", "]"))
    }

    fun toEntity(): User {
        return UserMapper.INSTANCE.toEntity(this)
    }

    fun isRole(id: Long): Boolean {
        return roles?.any { roleDto -> roleDto.id == id } ?: false
    }
}
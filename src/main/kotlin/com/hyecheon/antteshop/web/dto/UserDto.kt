package com.hyecheon.antteshop.web.dto

import com.hyecheon.antteshop.domains.entity.User
import com.hyecheon.antteshop.mapper.UserMapper
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
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
    var firstName: String? = null,
    var lastName: String? = null,
    var photos: FileInfoDto? = null,
    var enabled: Boolean? = null,
    var roles: MutableSet<RoleDto>? = null,
) {
    val password: String? = null
        get() {
            return if (field.isNullOrEmpty()) null
            else field
        }

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
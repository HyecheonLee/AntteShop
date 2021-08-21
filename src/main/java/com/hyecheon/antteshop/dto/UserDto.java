package com.hyecheon.antteshop.dto;

import com.hyecheon.antteshop.entity.User;
import com.hyecheon.antteshop.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.thymeleaf.util.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String photos;

    public boolean enabled;

    public Set<RoleDto> roles;

    public void addRole(RoleDto... roles) {
        for (RoleDto role : roles) {
            addRole(role);
        }
    }

    public void addRole(RoleDto role) {
        if (roles == null) roles = new HashSet<>();
        if (role != null) roles.add(role);
    }

    public String displayRole() {
        return roles.stream().map(RoleDto::getName).collect(Collectors.joining(",", "[", "]"));
    }

    public User toEntity() {
        return UserMapper.INSTANCE.toEntity(this);
    }

    public String getPassword() {
        if (StringUtils.isEmptyOrWhitespace(password)) return null;
        return password;
    }

    public boolean isRole(Long id) {
        return roles.stream().mapToLong(RoleDto::getId).anyMatch(value -> value == id);
    }
}

package com.hyecheon.antteshop.mapper;

import com.hyecheon.antteshop.domains.entity.User;
import com.hyecheon.antteshop.utils.ContextUtils;
import com.hyecheon.antteshop.web.dto.RoleDto;
import com.hyecheon.antteshop.web.dto.UserCreateDto;
import com.hyecheon.antteshop.web.dto.UserDto;
import com.hyecheon.antteshop.web.dto.UserUpdateDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", qualifiedByName = "passwordEncode")
    User toEntity(UserDto userDto);

    UserDto toDto(User userEntity);

    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", qualifiedByName = "toRoles")
    UserDto toDto(UserCreateDto userCreateDto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "roles", qualifiedByName = "toRoles")
    UserDto toDto(UserUpdateDto userUpdateDto);


    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", qualifiedByName = "passwordEncode")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UserDto source, @MappingTarget User target);

    @Named("toRoles")
    default Set<RoleDto> toRoles(Set<Long> ids) {
        return ids.stream().map(RoleDto::new)
                .collect(Collectors.toSet());
    }

    @Named("passwordEncode")
    default String passwordEncode(String password) {
        if (StringUtils.isEmptyOrWhitespace(password)) return null;
        final var passwordEncoder = ContextUtils.getBean(PasswordEncoder.class);
        assert passwordEncoder != null;
        return passwordEncoder.encode(password);
    }
}

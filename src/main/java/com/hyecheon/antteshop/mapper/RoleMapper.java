package com.hyecheon.antteshop.mapper;

import com.hyecheon.antteshop.entity.Role;
import com.hyecheon.antteshop.web.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role toEntity(RoleDto roleDto);

    RoleDto toDto(Role role);
}

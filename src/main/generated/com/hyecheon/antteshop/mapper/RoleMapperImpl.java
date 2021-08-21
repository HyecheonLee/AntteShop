package com.hyecheon.antteshop.mapper;

import com.hyecheon.antteshop.dto.RoleDto;
import com.hyecheon.antteshop.entity.Role;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-08-21T14:27:20+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Azul Systems, Inc.)"
)
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toEntity(RoleDto roleDto) {
        if ( roleDto == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleDto.getId() );
        role.setName( roleDto.getName() );
        role.setDescription( roleDto.getDescription() );

        return role;
    }

    @Override
    public RoleDto toDto(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setId( role.getId() );
        roleDto.setName( role.getName() );
        roleDto.setDescription( role.getDescription() );

        return roleDto;
    }
}

package com.hyecheon.antteshop.mapper;

import com.hyecheon.antteshop.dto.RoleDto;
import com.hyecheon.antteshop.dto.UserCreateDto;
import com.hyecheon.antteshop.dto.UserDto;
import com.hyecheon.antteshop.entity.Role;
import com.hyecheon.antteshop.entity.User;
import com.hyecheon.antteshop.web.dto.UserUpdateDto;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-08-21T14:27:20+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Azul Systems, Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setPassword( passwordEncode( userDto.getPassword() ) );
        user.setEmail( userDto.getEmail() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );
        user.setPhotos( userDto.getPhotos() );
        user.setEnabled( userDto.isEnabled() );
        user.setRoles( roleDtoSetToRoleSet( userDto.getRoles() ) );

        return user;
    }

    @Override
    public UserDto toDto(User userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( userEntity.getId() );
        userDto.setEmail( userEntity.getEmail() );
        userDto.setPassword( userEntity.getPassword() );
        userDto.setFirstName( userEntity.getFirstName() );
        userDto.setLastName( userEntity.getLastName() );
        userDto.setPhotos( userEntity.getPhotos() );
        userDto.setEnabled( userEntity.isEnabled() );
        userDto.setRoles( roleSetToRoleDtoSet( userEntity.getRoles() ) );

        return userDto;
    }

    @Override
    public UserDto toDto(UserCreateDto userCreateDto) {
        if ( userCreateDto == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setRoles( toRoles( userCreateDto.getRoles() ) );
        userDto.setEmail( userCreateDto.getEmail() );
        userDto.setPassword( userCreateDto.getPassword() );
        userDto.setFirstName( userCreateDto.getFirstName() );
        userDto.setLastName( userCreateDto.getLastName() );
        if ( userCreateDto.getEnabled() != null ) {
            userDto.setEnabled( userCreateDto.getEnabled() );
        }

        return userDto;
    }

    @Override
    public UserDto toDto(UserUpdateDto userUpdateDto) {
        if ( userUpdateDto == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setRoles( toRoles( userUpdateDto.getRoles() ) );
        userDto.setPassword( userUpdateDto.getPassword() );
        userDto.setFirstName( userUpdateDto.getFirstName() );
        userDto.setLastName( userUpdateDto.getLastName() );
        if ( userUpdateDto.getEnabled() != null ) {
            userDto.setEnabled( userUpdateDto.getEnabled() );
        }

        return userDto;
    }

    @Override
    public void updateUser(UserDto source, User target) {
        if ( source == null ) {
            return;
        }

        if ( source.getPassword() != null ) {
            target.setPassword( passwordEncode( source.getPassword() ) );
        }
        if ( source.getId() != null ) {
            target.setId( source.getId() );
        }
        if ( source.getFirstName() != null ) {
            target.setFirstName( source.getFirstName() );
        }
        if ( source.getLastName() != null ) {
            target.setLastName( source.getLastName() );
        }
        if ( source.getPhotos() != null ) {
            target.setPhotos( source.getPhotos() );
        }
        target.setEnabled( source.isEnabled() );
        if ( target.getRoles() != null ) {
            Set<Role> set = roleDtoSetToRoleSet( source.getRoles() );
            if ( set != null ) {
                target.getRoles().clear();
                target.getRoles().addAll( set );
            }
        }
        else {
            Set<Role> set = roleDtoSetToRoleSet( source.getRoles() );
            if ( set != null ) {
                target.setRoles( set );
            }
        }
    }

    protected Role roleDtoToRole(RoleDto roleDto) {
        if ( roleDto == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleDto.getId() );
        role.setName( roleDto.getName() );
        role.setDescription( roleDto.getDescription() );

        return role;
    }

    protected Set<Role> roleDtoSetToRoleSet(Set<RoleDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Role> set1 = new HashSet<Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleDto roleDto : set ) {
            set1.add( roleDtoToRole( roleDto ) );
        }

        return set1;
    }

    protected RoleDto roleToRoleDto(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setId( role.getId() );
        roleDto.setName( role.getName() );
        roleDto.setDescription( role.getDescription() );

        return roleDto;
    }

    protected Set<RoleDto> roleSetToRoleDtoSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleDto> set1 = new HashSet<RoleDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleDto( role ) );
        }

        return set1;
    }
}

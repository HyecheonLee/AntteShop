package com.hyecheon.antteshop.services.impl

import com.hyecheon.antteshop.TestUsers
import com.hyecheon.antteshop.mapper.UserMapper
import com.hyecheon.antteshop.repositories.UserRepository
import com.hyecheon.antteshop.web.dto.UserDto
import com.hyecheon.antteshop.web.dto.UserUpdateDto
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyLong
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.Pageable
import java.util.*

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
@ExtendWith(MockitoExtension::class)
internal class UserServiceImplTest {
    @InjectMocks
    lateinit var userServiceImpl: UserServiceImpl

    @Mock
    lateinit var userRepository: UserRepository


    @DisplayName("1. users 시 userDto 타입 변환 테스트")
    @Test
    internal fun test1() {

        //given
        val pageUser = TestUsers.pageUser()

        //when
        val pageable = Pageable.ofSize(10)
        `when`(userRepository.findAll(pageable)).thenReturn(pageUser)

        //then
        val users = userServiceImpl.users(pageable)
        Assertions.assertThat(users.content[0]).isExactlyInstanceOf(UserDto::class.java)
    }

    @DisplayName("2. user update 테스트")
    @Test
    internal fun test2() {
        val testUser = TestUsers.user(1L)

        `when`(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser))

        val userUpdateDto = UserUpdateDto(firstName = "혜천")
        val userDto = UserMapper.INSTANCE.toDto(userUpdateDto)
        userDto.email = "xxx"
        val updateUser = userServiceImpl.save(1L, userDto)

        Assertions.assertThat(updateUser.email).isEqualTo(testUser.email)
        Assertions.assertThat(updateUser.firstName).isEqualTo(userUpdateDto.firstName)
    }
}
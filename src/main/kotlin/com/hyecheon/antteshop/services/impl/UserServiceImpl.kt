package com.hyecheon.antteshop.services.impl

import com.hyecheon.antteshop.mapper.UserMapper
import com.hyecheon.antteshop.repositories.UserRepository
import com.hyecheon.antteshop.services.UserService
import com.hyecheon.antteshop.utils.toDto
import com.hyecheon.antteshop.utils.update
import com.hyecheon.antteshop.web.dto.UserDto
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    private val mapper = UserMapper.INSTANCE
    override fun users(pageable: Pageable) = run {
        val pageUser = userRepository.findAll(pageable)
        pageUser.map(mapper::toDto)
    }

    override fun save(userDto: UserDto): UserDto {
        return userRepository.save(userDto.toEntity()).toDto()
    }

    override fun save(id: Long, userDto: UserDto): UserDto {
        val findUser = userRepository.findById(id).orElseThrow { UsernameNotFoundException("id가 존재 하지 않습니다.") }
        findUser.update(userDto)
        try {
            val toDto = findUser.toDto()
            return toDto
        } catch (e: Exception) {
            throw RuntimeException("")
        }
    }

    override fun existsByEmail(email: String) = run {
        userRepository.existsByEmail(email)
    }

    override fun findById(id: Long): UserDto {
        return userRepository.findById(id).orElseThrow { UsernameNotFoundException("id가 존재 하지 않습니다.") }.toDto()
    }

    override fun deleteById(id: Long) = run {
        if (!userRepository.existsById(id)) {
            throw UsernameNotFoundException("ID가 ${id}인 사용자를 찾을 수 없습니다.")
        }
        userRepository.deleteById(id)
    }
}
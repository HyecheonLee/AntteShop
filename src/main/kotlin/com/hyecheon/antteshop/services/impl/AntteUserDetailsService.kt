package com.hyecheon.antteshop.services.impl

import com.hyecheon.antteshop.domains.AntteUserDetails
import com.hyecheon.antteshop.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/29
 */
@Service
class AntteUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(email: String?): UserDetails {
        val user =
            userRepository.findByEmail(email) ?: throw UsernameNotFoundException("이메일 [$email] 이 있는 사용자를 찾을 수 없음")
        return AntteUserDetails(user)
    }
}
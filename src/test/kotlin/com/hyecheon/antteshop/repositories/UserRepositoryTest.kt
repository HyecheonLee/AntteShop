package com.hyecheon.antteshop.repositories

import com.hyecheon.antteshop.TestUsers
import com.hyecheon.antteshop.entity.User
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
@DataJpaTest
@Transactional
class UserRepositoryTest {
    @Autowired
    lateinit var userRepository: UserRepository

    @DisplayName("1. pageable 테스트")
    @Test
    internal fun test1() {
        val users = users()
        userRepository.saveAll(users)
        val pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "id")
        val pageUser = userRepository.findAll(pageable)
        Assertions.assertThat(pageUser.totalElements).isEqualTo(users.size.toLong())
        Assertions.assertThat(pageUser.content[0].email).isEqualTo(users.last().email)
    }

    fun users() = run {
        (1..100).map { i ->
            User.builder()
                .email("test-${i}@test.com")
                .password("12345678")
                .firstName("firstName-${i}")
                .lastName("lastName-${i}")
                .build()
        }
    }


    @DisplayName("2. 테스트 existsById")
    @Test
    internal fun test2() {
        val exists = userRepository.existsById(1L)

        Assertions.assertThat(exists).isFalse

    }
}
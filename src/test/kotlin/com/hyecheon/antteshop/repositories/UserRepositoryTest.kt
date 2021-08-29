package com.hyecheon.antteshop.repositories

import com.hyecheon.antteshop.domains.entity.User
import org.assertj.core.api.Assertions.assertThat
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
        assertThat(pageUser.totalElements).isEqualTo(users.size.toLong())
        assertThat(pageUser.content[0].email).isEqualTo(users.last().email)
    }

    fun users() = run {
        (1..100).map { i ->
            User(
                email = "test-${i}@test.com",
                password = "12345678",
                firstName = "firstName-${i}",
                lastName = "lastName-${i}",
            )
        }
    }


    @DisplayName("2. 테스트 existsById")
    @Test
    internal fun test2() {
        val exists = userRepository.existsById(1L)

        assertThat(exists).isFalse

    }

    @DisplayName("3. searchQuery 테스트")
    @Test
    internal fun test3() {
        val test1 = User(
            email = "test1@test.com",
            password = "12345678",
            firstName = "firstName",
            lastName = "lastName",
        )
        val test2 = User(
            email = "test2@test.com",
            password = "12345678",
            firstName = "hyecheon",
            lastName = "lee",
        )

        userRepository.save(test1)
        userRepository.save(test2)
        val pageable = PageRequest.of(0, 4)
        val pageUser = userRepository.findAll("hye", pageable)

        assertThat(pageUser.content.size).isOne
    }
}
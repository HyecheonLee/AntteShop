package com.hyecheon.antteshop

import com.hyecheon.antteshop.domains.entity.User
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
object TestUsers {
    fun users() = run {
        (1..100).map { i -> user(i.toLong()) }
    }

    fun pageUser(pageable: Pageable = PageRequest.ofSize(10)) = run {
        val users = users()

        PageImpl(users(), pageable, 100)
    }

    fun user(i: Long) = run {
        User(
            id = i,
            email = "test-${i}@test.com",
            password = "12345678",
            firstName = "firstName-${i}",
            lastName = "lastName-${i}"
        )
    }
}
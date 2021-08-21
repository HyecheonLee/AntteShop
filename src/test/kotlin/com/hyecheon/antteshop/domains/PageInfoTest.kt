package com.hyecheon.antteshop.domains

import com.hyecheon.antteshop.TestUsers
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
internal class PageInfoTest {

    @DisplayName("1. pageInfo 생성 테스트")
    @Test
    internal fun test1() {
        val pageUser = TestUsers.pageUser(PageRequest.of(5, 3))
        val create = PageInfo.create(pageUser, 4)
        println(create)
    }

    @DisplayName("2. 첫페이지 테스트")
    @Test
    internal fun test2() {
        val pageUser = TestUsers.pageUser(PageRequest.of(0, 3))
        val create = PageInfo.create(pageUser, 4)
        Assertions.assertThat(create?.isPrev).isFalse
        Assertions.assertThat(create?.isNext).isTrue()
    }

    @DisplayName("3. 마지막 페이지 테스트")
    @Test
    internal fun test3() {
//        val users = TestUsers.users()
        val pageUser = TestUsers.pageUser(Pageable.ofSize(100))
        val create = PageInfo.create(pageUser, 4)
        Assertions.assertThat(create?.isNext).isFalse
    }

    @DisplayName("4. pageList ")
    @Test
    internal fun test4() {
        val pageUser = TestUsers.pageUser(PageRequest.of(5, 4))
        val create = PageInfo.create(pageUser, 2)
        Assertions.assertThat(create?.pageList).isEqualTo((5 - 2..5 + 2).toList())
    }

}
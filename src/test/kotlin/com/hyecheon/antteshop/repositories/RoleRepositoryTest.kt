package com.hyecheon.antteshop.repositories

import com.hyecheon.antteshop.entity.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.Rollback
import java.util.List

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/16
 */
@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    lateinit var roleRepository: RoleRepository

    @DisplayName("1. role insert 테스트")
    @Test
    internal fun test1() {
        val role = Role.builder()
            .name("Admin")
            .description("모든 것을 관리")
            .build()
        val savedRole = roleRepository.save(role)
        assertThat(savedRole.id).isNotNull
    }

    @DisplayName("2. 여러 롤 생성 테스트")
    @Test
    internal fun `test2`() {
        val salesperson = Role.builder()
            .name("Salesperson")
            .description("제품 가격, 고객, 쇼핑, 주문 및 판매 보고서 관리")
            .build()

        val roleEditor = Role.builder()
            .name("Editor")
            .description("카테고리, 브랜드, 제품, 기사 및 메뉴 관리")
            .build()

        val roleShipper = Role.builder()
            .name("Shipper")
            .description("제품 보기, 주문 보기 및 주문 상태 업데이트")
            .build()

        val roleAssistant = Role.builder()
            .name("Assistant")
            .description("질문 및 리뷰 관리")
            .build()

        val roles = roleRepository.saveAll(List.of(salesperson, roleEditor, roleShipper, roleShipper, roleAssistant))

        for (role in roles) {
            assertThat(role.id).isNotNull
        }
    }
}
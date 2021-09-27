package com.hyecheon.antteshop.repositories

import com.hyecheon.antteshop.domains.entity.Category
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/09/27
 */
@SpringBootTest
@Transactional
@Rollback(false)
class CategoryRepositoryTest {
    @Autowired
    lateinit var repo: CategoryRepository


    @DisplayName("3. 카테고리 가져오기")
    @Test
    internal fun test_3() {

        val optionalCategory = repo.findById(9)
        val category = optionalCategory.get()
        println(category)
    }

    @DisplayName("2. 서브 카테고리 생성")
    @Test
    internal fun test_2() {

        val parent = Category(name = "Computers")
        val subCategory = Category(name = "Desktops")

        val savedParent = repo.save(parent)
        subCategory.parent = savedParent

        val savedSubCategory = repo.save(subCategory)
        savedParent.addChild(savedSubCategory)
        repo.save(savedParent)
        assertThat(savedSubCategory.parent?.id).isNotNull
        assertThat(savedSubCategory.parent?.name).isEqualTo("Computers")

    }


    @DisplayName("1. 카테고리 생성 테스트")
    @Test
    internal fun test_1() {
        val category = Category(name = "Computers")
        val savedCategory = repo.save(category)

        assertThat(savedCategory.id).isNotNull
    }
}
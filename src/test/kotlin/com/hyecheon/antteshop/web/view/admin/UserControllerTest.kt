package com.hyecheon.antteshop.web.view.admin

import com.hyecheon.antteshop.TestUsers
import com.hyecheon.antteshop.services.UserService
import com.hyecheon.antteshop.utils.toDto
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.validation.BindingResult
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.absolute
import kotlin.io.path.deleteIfExists
import kotlin.io.path.exists


/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
@SpringBootTest
@AutoConfigureMockMvc
internal class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var userService: UserService


    @DisplayName("1. users Url 확인")
    @Test
    internal fun test1() {
        val pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "id")
        `when`(userService.users(pageable)).thenReturn(TestUsers.pageUser().map { it.toDto() })
        val result = mockMvc.perform(
            get("/admin/users")
                .contentType(MediaType.TEXT_HTML))
            .andExpect(status().isOk)
            .andReturn()
        val modelAndView = result.modelAndView

        assertThat(modelAndView).isNotNull
        assertThat(modelAndView?.viewName).isEqualTo("admin/users/index")
        val model = modelAndView?.model
        assertThat(model).containsKey("users")
    }

    @DisplayName("2. 유저 생성 성공시 302")
    @Test
    internal fun test2() {
        val creatUserForm = post("/admin/users/new")
            .param("email", "rainbow0616@naver.com")
            .param("password", "1234")
            .param("firstName", "혜천")
            .param("lastName", "이")
            .param("enable", "true")
            .param("roles[]", "1", "2", "3")
            .contentType(MediaType.TEXT_HTML)
        val result = mockMvc.perform(
            creatUserForm)
            .andExpect(status().is3xxRedirection)
            .andReturn()
    }

    @DisplayName("3. 유저 생성 실패시 form 으로")
    @Test
    internal fun test3() {
        val creatUserForm = post("/admin/users/new")
            .param("email", "rainbow0616")
            .param("password", "1234")
            .param("firstName", "혜천")
            .param("lastName", "이")
            .param("enable", "true")
            .param("roles[]", "1", "2", "3")
            .contentType(MediaType.TEXT_HTML)
        val result = mockMvc.perform(
            creatUserForm)
            .andExpect(status().isOk)
            .andReturn()
        val modelAndView = result.modelAndView
        assertThat(modelAndView?.viewName).isEqualTo("admin/users/new")
    }

    @DisplayName("4. 유저 생성시 email 체크")
    @Test
    internal fun test4() {
        val creatUserForm = post("/admin/users/new")
            .param("email", "rainbow0616")
            .param("password", "1234")
            .param("firstName", "혜천")
            .param("lastName", "이")
            .param("enable", "true")
            .param("roles[]", "1", "2", "3")
            .contentType(MediaType.TEXT_HTML)
        val result = mockMvc.perform(
            creatUserForm)
            .andExpect(status().isOk)
            .andReturn()
        val model = result.modelAndView?.model
        val bindingResult = model?.get("org.springframework.validation.BindingResult.user")
        assertThat(bindingResult).isInstanceOf(BindingResult::class.java)
        if (bindingResult is BindingResult) {
            assertThat(bindingResult.errorCount).isOne
            val fieldError = bindingResult.getFieldError("email")
            assertThat(fieldError).isNotNull
        }
    }

    @DisplayName("5. 유저 생성시 password 체크(4~20)")
    @Test
    internal fun test5() {
        val creatUserForm = post("/admin/users/new")
            .param("email", "rainbow0616@naver.com")
            .param("password", "123")
            .param("firstName", "혜천")
            .param("lastName", "이")
            .param("enable", "true")
            .param("roles[]", "1", "2", "3")
            .contentType(MediaType.TEXT_HTML)
        val result = mockMvc.perform(
            creatUserForm)
            .andExpect(status().isOk)
            .andReturn()
        val model = result.modelAndView?.model
        val bindingResult = model?.get("org.springframework.validation.BindingResult.user")
        assertThat(bindingResult).isInstanceOf(BindingResult::class.java)
        if (bindingResult is BindingResult) {
            assertThat(bindingResult.errorCount).isOne
            val fieldError = bindingResult.getFieldError("password")
            assertThat(fieldError).isNotNull
        }
    }

    @DisplayName("6. 이메일 중복검사")
    @Test
    internal fun test6() {
        `when`(userService.existsByEmail(anyString())).thenReturn(true)
        val creatUserForm = post("/admin/users/new")
            .param("email", "rainbow0616@naver.com")
            .param("password", "12345678")
            .param("firstName", "혜천")
            .param("lastName", "이")
            .param("enable", "true")
            .param("roles[]", "1", "2", "3")
            .contentType(MediaType.TEXT_HTML)
        val result = mockMvc.perform(
            creatUserForm)
            .andExpect(status().isOk)
            .andReturn()
        val model = result.modelAndView?.model
        val bindingResult = model?.get("org.springframework.validation.BindingResult.user")
        assertThat(bindingResult).isInstanceOf(BindingResult::class.java)
        if (bindingResult is BindingResult) {
            assertThat(bindingResult.errorCount).isOne
            val fieldError = bindingResult.getFieldError("email")
            assertThat(fieldError).isNotNull
        }
    }

    @DisplayName("7. uploadFileTest")
    @Test
    internal fun test7() {
        val uploadDir = Path.of("", "user-photos/1")
        val absolute = uploadDir.absolute()
        val saveFile = File(absolute.toUri())
        saveFile.listFiles().forEach { file -> file.delete() }
        assertThat(saveFile.listFiles().size).isEqualTo(0)

//        val uploadedFile = uploadDir.resolve("default-user.png")
//        uploadedFile.deleteIfExists()

//        whenever(userService.save(UserDto())).thenReturn(UserDto())
        val workingDir = Path.of("", "src/test/resources/static/images")
        val file = workingDir.resolve("default-user.png")
        val imageFile = MockMultipartFile("image",
            "default-user.png",
            null,
            Files.readAllBytes(file)
        )
        val result = mockMvc.perform(multipart("/admin/users/1/update")
            .file(imageFile)
            .param("email", "rainbow0616@naver.com")
            .param("password", "1234")
            .param("firstName", "혜천")
            .param("lastName", "이")
            .param("enable", "true")
            .param("roles[]", "1", "2", "3")
            .contentType(MediaType.TEXT_HTML)
        )
            .andExpect(status().is3xxRedirection)
            .andReturn()
        assertThat(saveFile.listFiles().size).isEqualTo(1)
    }
}
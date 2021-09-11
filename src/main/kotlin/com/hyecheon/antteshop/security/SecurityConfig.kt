package com.hyecheon.antteshop.security

import com.hyecheon.antteshop.services.impl.AntteUserDetailsService
import org.springframework.boot.autoconfigure.security.StaticResourceLocation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/13
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: AntteUserDetailsService,
) : WebSecurityConfigurerAdapter() {


    fun authenticationProvider() = run {
        DaoAuthenticationProvider().apply {
            setUserDetailsService(userDetailsService)
            setPasswordEncoder(passwordEncoder())
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = run {
        BCryptPasswordEncoder()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/admin/users/**").hasAnyAuthority("Admin")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .successHandler { _, response, _ ->
                response.sendRedirect("/admin")
            }
            .permitAll()
            .usernameParameter("email")
            .and()
            .logout()
            .permitAll()
            .invalidateHttpSession(true).deleteCookies("JSESSIONID")
            .and().rememberMe()
//            .tokenValiditySeconds(60 * 60 * 24 * 30)

    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/resources/**",
            "/css/**",
            "/fonts/**",
            "/js/**",
            "/less/**",
            "/scss/**",
            "/images/**",
            "/webjars/**")
    }
}
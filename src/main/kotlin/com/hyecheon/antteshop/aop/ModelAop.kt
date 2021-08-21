package com.hyecheon.antteshop.aop

import com.hyecheon.antteshop.services.RoleService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.ui.Model
import javax.servlet.http.HttpServletResponse

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/15
 */
@Aspect
@Component
class ModelAop {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Autowired
    lateinit var roleService: RoleService
    lateinit var httpServletResponse: HttpServletResponse

    @Around("@annotation(com.hyecheon.antteshop.annotation.BasicModel)")
    fun defaultModel(joinPoint: ProceedingJoinPoint): Any = run {
        joinPoint.args.forEach { println(it) }
        
        /*val roles = roleService.findAll()
        if (model != null) {
            model.addAttribute("roles", roles)
            model.addAttribute("title", "test")
        }*/
        log.info("defaultModel")
//        joinPoint.proceed()
    }
}
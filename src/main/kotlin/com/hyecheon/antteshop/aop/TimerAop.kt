package com.hyecheon.antteshop.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/13
 */
@ExperimentalTime
@Aspect
@Component
class TimerAop {
    private val log = LoggerFactory.getLogger(this::class.java)


    @Around("@annotation(com.hyecheon.antteshop.annotation.Timer)")
    fun timer(joinPoint: ProceedingJoinPoint): Any? = run {
        log.info("execution [ ${joinPoint.signature.toShortString()} ] start")
        val measureTimedValue = measureTimedValue { joinPoint.proceed() }
        log.info("duration : {}", measureTimedValue.duration)
        log.info("execution [ ${joinPoint.signature.toShortString()} ] end")
        measureTimedValue.value
    }
}
package com.hyecheon.antteshop.domains.entity

import lombok.Data
import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@Where(clause = "deleted = 0")
abstract class BaseEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt = LocalDateTime.now()

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt = LocalDateTime.now()

    @Column(name = "isDeleted", nullable = false)
    var deleted: Boolean = false
}
package com.hyecheon.antteshop.entity

import com.hyecheon.antteshop.domains.entity.BaseEntity
import lombok.Data
import lombok.EqualsAndHashCode
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
@MappedSuperclass
@EntityListeners(
    AuditingEntityListener::class)
abstract class BaseWithUserEntity : BaseEntity() {
    @CreatedBy
    @Column(updatable = false)
    var createdBy: String? = null

    @LastModifiedBy
    var updatedBy: String? = null
}
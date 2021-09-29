package com.hyecheon.antteshop.domains.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.hyecheon.antteshop.entity.BaseWithUserEntity
import com.hyecheon.antteshop.utils.ContextUtils
import javax.persistence.*

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/09/27
 */

@Entity
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(length = 128, nullable = false, unique = true)
    var name: String = "",

    @Column(length = 64, nullable = false, unique = true)
    var alias: String = name,

    @Column(length = 128, nullable = false)
    var image: String = "",

    var level: Int = 0,

    var enabled: Boolean = false,

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    var parent: Category? = null,

    @OneToMany
    var child: MutableSet<Category> = mutableSetOf(),

    ) : BaseWithUserEntity() {
    fun addChild(childCategory: Category) {
        child.add(childCategory)
    }

    fun dispCategoryName() = run {
        """${"--".repeat(level)} $name"""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category

        if (id != other.id) return false
        if (name != other.name) return false
        if (alias != other.alias) return false
        if (image != other.image) return false
        if (enabled != other.enabled) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + alias.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + enabled.hashCode()
        return result
    }

    override fun toString(): String {
        return ContextUtils.jsonString(this)
    }
}

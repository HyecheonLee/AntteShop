package com.hyecheon.antteshop.domains.entity

import com.hyecheon.antteshop.entity.UserFileInfo
import lombok.*
import javax.persistence.*

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/14
 */
@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(length = 128, nullable = false, unique = true, updatable = false)
    var email: String? = null,

    @Column(length = 64, nullable = false)
    var password: String? = null,

    @Column(length = 45, nullable = false)
    var firstName: String? = null,

    @Column(length = 45, nullable = false)
    var lastName: String? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    var photos: UserFileInfo? = null,
    var enabled: Boolean = false,

    @ToString.Exclude
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")])
    var roles: MutableSet<Role> = HashSet(),
) : BaseEntity() {
    fun addRole(vararg roles: Role?) {
        for (role in roles) {
            if (role != null) this.roles.add(role)
        }
    }
}
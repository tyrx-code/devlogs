package com.tomas.devlogs.domain.entity

import com.tomas.devlogs.domain.enums.Role
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "users")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(unique = true, nullable = false)
    var username: String,

    @Column(unique = true, nullable = false)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    var role: Role = Role.USER,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var entries: MutableList<Entry> = mutableListOf()
)
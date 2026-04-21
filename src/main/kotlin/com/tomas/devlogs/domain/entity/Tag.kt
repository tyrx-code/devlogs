package com.tomas.devlogs.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tags")
class Tag (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    open var id: UUID? = null,

    @Column(unique = true, nullable = false)
    open var name: String = "",

    @ManyToMany(mappedBy = "tags")
    var entries: MutableSet<Entry> = mutableSetOf()
)
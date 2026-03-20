package com.tomas.devlogs.domain.entity
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "code_snippets")
open class CodeSnippet(
    @Id
    @GeneratedValue
    open var id: UUID? = null,
    @Column(nullable = false)
    open var language: String = "",
    @Column(nullable = false, columnDefinition = "TEXT")
    open var code: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entry_id")
    open var entry: Entry? = null
)
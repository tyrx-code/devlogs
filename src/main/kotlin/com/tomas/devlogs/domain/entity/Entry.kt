package com.tomas.devlogs.domain.entity
import com.tomas.devlogs.domain.enums.Difficulty
import com.tomas.devlogs.domain.enums.Mood
import com.tomas.devlogs.domain.enums.Visibility
import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "entries")
open class Entry (

    @Id @GeneratedValue
    open var id: UUID? = null,
    @Column(nullable = false)
    open var title: String = "",
    @Column(nullable = false, columnDefinition = "TEXT")
    open var content: String = "",
    @Enumerated(EnumType.STRING)
    open var difficulty: Difficulty = Difficulty.MEDIUM,
    @Enumerated(EnumType.STRING)
    open var mood: Mood = Mood.NEUTRAL,
    @Enumerated(EnumType.STRING)
    open var visibility: Visibility = Visibility.PRIVATE,
    open val createdAt: Instant = Instant.now(),
    open var updatedAt: Instant? = null,

    @OneToMany(
        mappedBy = "entry",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    open var snippets: MutableList<CodeSnippet> = mutableListOf(),

    @ManyToMany
    @JoinTable(
        name = "entry_tags",
        joinColumns = [JoinColumn(name = "entry_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    open var tags: MutableSet<Tag> = mutableSetOf()
){
    fun addSnippet(snippet: CodeSnippet) {
        snippets.add(snippet)
        snippet.entry = this
    }
    open fun addTag(tag: Tag) {
        tags.add(tag)
    }
}

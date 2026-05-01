package com.tomas.devlogs.dto
import com.tomas.devlogs.domain.enums.*
import java.time.Instant
import java.util.UUID

data class EntryResponse (
    val id: UUID?,
    val title: String,
    val content: String,
    val difficulty: Difficulty,
    val mood: Mood,
    val visibility: Visibility,
    val tags: Set<String>,
    val createdAt: Instant?,
    val updatedAt: Instant?
)
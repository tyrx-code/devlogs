package com.tomas.devlogs.mapper
import com.tomas.devlogs.domain.entity.Entry
import com.tomas.devlogs.dto.EntryCreateRequest
import com.tomas.devlogs.dto.EntryResponse

object EntryMapper {

    fun toEntity(request: EntryCreateRequest): Entry {
        return Entry(
            title = request.title,
            content = request.content,
            difficulty = request.difficulty,
            mood = request.mood,
            visibility = request.visibility,
            tags = mutableSetOf()
        )
    }
    fun toResponse(entry: Entry): EntryResponse {
        return EntryResponse(
            id = entry.id,
            title = entry.title,
            content = entry.content,
            difficulty = entry.difficulty,
            mood = entry.mood,
            visibility = entry.visibility,
            tags = entry.tags.map { it.name }.toSet(),
            createdAt = entry.createdAt,
            updatedAt = entry.updatedAt
        )
    }
}
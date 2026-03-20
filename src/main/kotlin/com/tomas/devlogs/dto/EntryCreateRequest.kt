package com.tomas.devlogs.dto
import com.tomas.devlogs.domain.enums.*
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class EntryCreateRequest (
    @field:Schema(description = "Title of the entry", example = "My first log")
    @field:NotBlank(message = "Title must not be empty")
    val title: String,

    @field:Schema(description = "Main content of the entry")
    @field:NotBlank(message = "Content must not be empty")
    val content: String,

    @field:NotNull
    val difficulty: Difficulty,

    @field:NotNull
    val mood: Mood,

    @field:Schema(description = "Visibility of the entry", example = "PUBLIC")
    @field:NotNull
    val visibility: Visibility,

    val tags: Set<String> = emptySet()

)
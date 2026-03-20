package com.tomas.devlogs.dto

import com.tomas.devlogs.domain.enums.Visibility
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class EntryUpdateRequest (
    @field:NotBlank(message = "Title is required")
    val title: String,

    @field:NotBlank(message = "Content is required")
    val content: String,

    @field:NotNull(message = "Visibility is required")
    val visibility: Visibility,

    val tags: Set<String>?
)
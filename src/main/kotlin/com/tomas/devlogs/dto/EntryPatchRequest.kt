package com.tomas.devlogs.dto

import com.tomas.devlogs.domain.entity.Tag
import com.tomas.devlogs.domain.enums.Visibility

data class EntryPatchRequest (
    val title: String? = null,
    val content: String? = null,
    val visibility: Visibility? = null,
    val tags: Set<String>? = null
)
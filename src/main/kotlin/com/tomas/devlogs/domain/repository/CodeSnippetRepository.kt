package com.tomas.devlogs.domain.repository

import com.tomas.devlogs.domain.entity.CodeSnippet
import com.tomas.devlogs.domain.enums.SnippetLanguage

interface CodeSnippetRepository {
    fun findByLanguage(language: SnippetLanguage): List<CodeSnippet>
}
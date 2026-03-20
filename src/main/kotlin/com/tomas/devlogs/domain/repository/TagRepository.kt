package com.tomas.devlogs.domain.repository

import com.tomas.devlogs.domain.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TagRepository: JpaRepository<Tag, UUID> {
    fun findByName(name: String): Tag?
}
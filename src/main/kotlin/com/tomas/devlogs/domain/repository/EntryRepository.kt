package com.tomas.devlogs.domain.repository

import com.tomas.devlogs.domain.entity.Entry
import com.tomas.devlogs.domain.entity.User
import com.tomas.devlogs.domain.enums.Visibility
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface EntryRepository : JpaRepository<Entry, UUID> {

    fun findByVisibility(visibility: Visibility): List<Entry>
    fun findByTitleContainingIgnoreCase(keyword: String): List<Entry>
    fun findAllByUser(user: User): List<Entry>
    fun findByIdAndUser(id: UUID, user: User): Optional<Entry>
}
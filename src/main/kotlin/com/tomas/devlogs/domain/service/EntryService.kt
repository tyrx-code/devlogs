package com.tomas.devlogs.domain.service
import com.tomas.devlogs.domain.entity.Entry
import com.tomas.devlogs.domain.entity.Tag
import com.tomas.devlogs.domain.entity.User
import com.tomas.devlogs.domain.enums.Visibility
import com.tomas.devlogs.domain.repository.EntryRepository
import com.tomas.devlogs.domain.exception.*
import com.tomas.devlogs.domain.repository.TagRepository
import com.tomas.devlogs.domain.repository.UserRepository
import com.tomas.devlogs.dto.EntryPatchRequest
import com.tomas.devlogs.dto.EntryUpdateRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional
@Service
class EntryService (
    private val entryRepository: EntryRepository,
    private val tagRepository: TagRepository,
    private val userRepository: UserRepository
) {
    private fun getCurrentUser(): User {
        val username = SecurityContextHolder.getContext().authentication.name

        return userRepository.findByUsername(username)
            .orElseThrow {
                RuntimeException("User not found")
            }
    }

    fun create(entry: Entry, tagNames: Set<String>): Entry {
        val currentUser = getCurrentUser()
        entry.user = currentUser

        val tags = resolveTags(tagNames)
        entry.tags.addAll(tags)

        return entryRepository.save(entry)
    }
    fun findById(id: UUID): Entry {
        val currentUser = getCurrentUser()

        return entryRepository.findByIdAndUser(id, currentUser).orElseThrow {
            ResourceNotFoundException("Entry not found with id: $id")
        }
    }

    fun findAll(): List<Entry> {
        val currentUser = getCurrentUser()

        return entryRepository.findAllByUser(currentUser)
    }

    fun findPublicEntries(): List<Entry> {
        return entryRepository.findByVisibility(Visibility.PUBLIC)
    }
    fun delete(id: UUID) {
        val currentUser = getCurrentUser()

        val entry = entryRepository.findByIdAndUser(id, currentUser)
            .orElseThrow {
                ResourceNotFoundException("Entry not found")
            }

        entryRepository.delete(entry)
    }
    fun update(id: UUID, updated: Entry): Entry {
        val existing = entryRepository.findById(id).orElseThrow { IllegalArgumentException("Entry not found") }

        existing.title = updated.title
        existing.content = updated.content
        existing.difficulty = updated.difficulty
        existing.mood = updated.mood
        existing.visibility = updated.visibility
        existing.updatedAt = java.time.Instant.now()

        return existing
    }

    fun updateEntry(id: UUID, entryUpd: EntryUpdateRequest): Entry {
        val entry = entryRepository.findByIdAndUser(id, getCurrentUser()).orElseThrow {
            ResourceNotFoundException("Entry with id $id not found")
        }

        entry.title = entryUpd.title
        entry.content = entryUpd.content
        entry.visibility = entryUpd.visibility
        entry.updatedAt = java.time.Instant.now()

        if (entryUpd.tags != null){
            entry.tags.clear()
            entry.tags.addAll(resolveTags(entryUpd.tags))
        }

        val updated = entryRepository.save(entry)

        return updated
    }

    fun patchEntry(id: UUID, entryPatch: EntryPatchRequest): Entry {
        val entry = entryRepository.findById(id).orElseThrow { ResourceNotFoundException("Entry with id $id not found") }

        entryPatch.title?.let { entry.title = it }
        entryPatch.content?.let { entry.content = it }
        entryPatch.visibility?.let { entry.visibility = it }

        entry.updatedAt = java.time.Instant.now()

        if (entryPatch.tags != null) {
            entry.tags.clear()
            entry.tags.addAll(resolveTags(entryPatch.tags))
        }

        val updated = entryRepository.save(entry)

        return updated
    }

    fun resolveTags(tagNames: Set<String>): Set<Tag> {
        val normalizedNames = tagNames
            .map { it.trim().lowercase() }
            .toSet()

        if (normalizedNames.isEmpty()) return emptySet()

        val existingTags = tagRepository.findAllByNameIn(normalizedNames)
        val existingMap = existingTags.associateBy { it.name }

        val result = mutableSetOf<Tag>()

        for (name in normalizedNames) {
            val tag = existingMap[name] ?: Tag(name = name)
            result.add(tag)
        }

        val newTags = result.filter { it.id == null }

        try {
            tagRepository.saveAll(newTags)
        } catch (ex: Exception) {
            // fallback for race condition
            return tagRepository.findAllByNameIn(normalizedNames).toSet()
        }
        return result
    }

}
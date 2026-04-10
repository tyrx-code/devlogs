package com.tomas.devlogs.controller
import com.tomas.devlogs.mapper.EntryMapper
import com.tomas.devlogs.domain.service.EntryService
import com.tomas.devlogs.dto.EntryCreateRequest
import com.tomas.devlogs.dto.EntryPatchRequest
import com.tomas.devlogs.dto.EntryResponse
import com.tomas.devlogs.dto.EntryUpdateRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize

@Tag(name = "Entries", description = "Operations related to dev log entries")
@RestController
@RequestMapping("/api/entries")
class EntryController (private val entryService: EntryService) {

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    fun create(@Valid @RequestBody request: EntryCreateRequest): ResponseEntity<EntryResponse> {

        val entity = EntryMapper.toEntity(request)
        val saved = entryService.create(entity, request.tags)

        return ResponseEntity.ok(EntryMapper.toResponse(saved))
    }

    @Operation(summary = "Get entry by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Entry found"),
            ApiResponse(responseCode = "404", description = "Entry not found")
        ]
    )

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    fun getAll(): ResponseEntity<List<EntryResponse>> {

        val entries = entryService.findAll().map { EntryMapper.toResponse(it) }

        return ResponseEntity.ok(entries)
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<EntryResponse> {

        val entry = entryService.findById(id)

        return ResponseEntity.ok(EntryMapper.toResponse(entry))
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {

        entryService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    fun updateEntry(@PathVariable id: UUID, @Valid @RequestBody request: EntryUpdateRequest): ResponseEntity<EntryResponse?> {

        val updated = entryService.updateEntry(id, request)
        return ResponseEntity.ok(EntryMapper.toResponse(updated))
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PatchMapping("/{id}")
    fun patchEntry(@PathVariable id: UUID, @RequestBody request: EntryPatchRequest): ResponseEntity<EntryResponse> {

        val updated = entryService.patchEntry(id, request)
        return ResponseEntity.ok(EntryMapper.toResponse(updated))
    }

}
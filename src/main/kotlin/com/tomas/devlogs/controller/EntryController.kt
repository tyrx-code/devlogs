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

@Tag(name = "Entries", description = "Operations related to dev log entries")
@RestController
@RequestMapping("/api/entries")
class EntryController (private val entryService: EntryService) {

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
    @GetMapping
    fun getAll(): ResponseEntity<List<EntryResponse>> {

        val entries = entryService.findAll().map { EntryMapper.toResponse(it) }

        return ResponseEntity.ok(entries)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<EntryResponse> {

        val entry = entryService.findById(id)

        return ResponseEntity.ok(EntryMapper.toResponse(entry))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {

        entryService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun updateEntry(@PathVariable id: UUID, @Valid @RequestBody request: EntryUpdateRequest): ResponseEntity<EntryResponse?> {

        val updated = entryService.updateEntry(id, request)
        return ResponseEntity.ok(EntryMapper.toResponse(updated))
    }

    @PatchMapping("/{id}")
    fun patchEntry(@PathVariable id: UUID, @RequestBody request: EntryPatchRequest): ResponseEntity<EntryResponse> {

        val updated = entryService.patchEntry(id, request)
        return ResponseEntity.ok(EntryMapper.toResponse(updated))
    }

}
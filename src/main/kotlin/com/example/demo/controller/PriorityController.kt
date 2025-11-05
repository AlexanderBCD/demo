package com.example.demo.controller

import com.example.demo.dto.PriorityRequest
import com.example.demo.dto.PriorityResponse
import com.example.demo.services.PriorityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/priorities")
class PriorityController @Autowired constructor(
    private val priorityService: PriorityService
) {

    @GetMapping
    fun getAllPriorities(): ResponseEntity<List<PriorityResponse>> {
        val priorities = priorityService.getAllPriorities()
        return ResponseEntity.ok(priorities)
    }

    @GetMapping("/{id}")
    fun getPriorityById(@PathVariable id: Long): ResponseEntity<PriorityResponse> {
        val priority = priorityService.getPriorityById(id)
        return if (priority != null) {
            ResponseEntity.ok(priority)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createPriority(@RequestBody priorityRequest: PriorityRequest): ResponseEntity<PriorityResponse> {
        val createdPriority = priorityService.createPriority(priorityRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPriority)
    }

    @PutMapping("/{id}")
    fun updatePriority(
        @PathVariable id: Long,
        @RequestBody priorityRequest: PriorityRequest
    ): ResponseEntity<PriorityResponse> {
        val updatedPriority = priorityService.updatePriority(id, priorityRequest)
        return if (updatedPriority != null) {
            ResponseEntity.ok(updatedPriority)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deletePriority(@PathVariable id: Long): ResponseEntity<Void> {
        val deleted = priorityService.deletePriority(id)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

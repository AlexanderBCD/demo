package com.example.demo.controller

import com.example.demo.dto.GrouperRequest
import com.example.demo.dto.GrouperResponse
import com.example.demo.services.GrouperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/groupers")
class GrouperController @Autowired constructor(
    private val grouperService: GrouperService
) {

    @GetMapping
    fun getAllGroupers(): ResponseEntity<List<GrouperResponse>> {
        val groupers = grouperService.getAllGroupers()
        return ResponseEntity.ok(groupers)
    }

    @GetMapping("/{id}")
    fun getGrouperById(@PathVariable id: Long): ResponseEntity<GrouperResponse> {
        val grouper = grouperService.getGrouperById(id)
        return if (grouper != null) {
            ResponseEntity.ok(grouper)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createGrouper(@RequestBody grouperRequest: GrouperRequest): ResponseEntity<GrouperResponse> {
        val createdGrouper = grouperService.createGrouper(grouperRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGrouper)
    }

    @PutMapping("/{id}")
    fun updateGrouper(
        @PathVariable id: Long,
        @RequestBody grouperRequest: GrouperRequest
    ): ResponseEntity<GrouperResponse> {
        val updatedGrouper = grouperService.updateGrouper(id, grouperRequest)
        return if (updatedGrouper != null) {
            ResponseEntity.ok(updatedGrouper)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteGrouper(@PathVariable id: Long): ResponseEntity<Void> {
        val deleted = grouperService.deleteGrouper(id)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

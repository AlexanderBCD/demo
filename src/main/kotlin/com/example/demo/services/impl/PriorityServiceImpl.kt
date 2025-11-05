package com.example.demo.services.impl

import com.example.demo.dto.PriorityRequest
import com.example.demo.dto.PriorityResponse
import com.example.demo.model.Priorities
import com.example.demo.repository.PriorityRepository
import com.example.demo.services.PriorityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PriorityServiceImpl @Autowired constructor(
    private val priorityRepository: PriorityRepository
) : PriorityService {

    override fun getAllPriorities(): List<PriorityResponse> {
        return priorityRepository.getAll().map { PriorityResponse.fromEntity(it) }
    }

    override fun getPriorityById(id: Long): PriorityResponse? {
        return priorityRepository.getById(id)?.let { PriorityResponse.fromEntity(it) }
    }

    override fun createPriority(priorityRequest: PriorityRequest): PriorityResponse {
        val priority = Priorities(
            name = priorityRequest.name
        )
        val createdPriority = priorityRepository.create(priority)
        return PriorityResponse.fromEntity(createdPriority)
    }

    override fun updatePriority(id: Long, priorityRequest: PriorityRequest): PriorityResponse? {
        val existingPriority = priorityRepository.getById(id) ?: return null
        
        val updatedPriority = existingPriority.copy(
            name = priorityRequest.name
        )
        
        val rowsUpdated = priorityRepository.update(updatedPriority)
        return if (rowsUpdated > 0) PriorityResponse.fromEntity(updatedPriority) else null
    }

    override fun deletePriority(id: Long): Boolean {
        return priorityRepository.delete(id)
    }
}

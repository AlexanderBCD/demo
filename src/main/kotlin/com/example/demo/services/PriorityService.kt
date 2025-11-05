package com.example.demo.services

import com.example.demo.dto.PriorityRequest
import com.example.demo.dto.PriorityResponse

interface PriorityService {
    fun getAllPriorities(): List<PriorityResponse>
    fun getPriorityById(id: Long): PriorityResponse?
    fun createPriority(priorityRequest: PriorityRequest): PriorityResponse
    fun updatePriority(id: Long, priorityRequest: PriorityRequest): PriorityResponse?
    fun deletePriority(id: Long): Boolean
}

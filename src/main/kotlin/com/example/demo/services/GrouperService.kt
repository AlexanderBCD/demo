package com.example.demo.services

import com.example.demo.dto.GrouperRequest
import com.example.demo.dto.GrouperResponse

interface GrouperService {
    fun getAllGroupers(): List<GrouperResponse>
    fun getGrouperById(id: Long): GrouperResponse?
    fun createGrouper(grouperRequest: GrouperRequest): GrouperResponse
    fun updateGrouper(id: Long, grouperRequest: GrouperRequest): GrouperResponse?
    fun deleteGrouper(id: Long): Boolean
}

package com.example.demo.services.impl

import com.example.demo.dto.GrouperRequest
import com.example.demo.dto.GrouperResponse
import com.example.demo.model.Groupers
import com.example.demo.repository.GrouperRepository
import com.example.demo.services.GrouperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GrouperServiceImpl @Autowired constructor(
    private val grouperRepository: GrouperRepository
) : GrouperService {

    override fun getAllGroupers(): List<GrouperResponse> {
        return grouperRepository.getAll().map { GrouperResponse.fromEntity(it) }
    }

    override fun getGrouperById(id: Long): GrouperResponse? {
        return grouperRepository.getById(id)?.let { GrouperResponse.fromEntity(it) }
    }

    override fun createGrouper(grouperRequest: GrouperRequest): GrouperResponse {
        val grouper = Groupers(
            name = grouperRequest.name
        )
        val createdGrouper = grouperRepository.create(grouper)
        return GrouperResponse.fromEntity(createdGrouper)
    }

    override fun updateGrouper(id: Long, grouperRequest: GrouperRequest): GrouperResponse? {
        val existingGrouper = grouperRepository.getById(id) ?: return null
        
        val updatedGrouper = existingGrouper.copy(
            name = grouperRequest.name
        )
        
        val rowsUpdated = grouperRepository.update(updatedGrouper)
        return if (rowsUpdated > 0) GrouperResponse.fromEntity(updatedGrouper) else null
    }

    override fun deleteGrouper(id: Long): Boolean {
        return grouperRepository.delete(id)
    }
}

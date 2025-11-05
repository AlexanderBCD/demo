package com.example.demo.dto

import com.example.demo.model.Tasks
import java.util.*

data class TaskResponse(
    val id: Long,
    val orderIndex: Int,
    val title: String,
    val description: String? = null,
    val dateLimit: Date? = null,
    val grouperId: Long? = null,
    val priorityId: Long? = null
) {
    companion object {
        fun fromEntity(task: Tasks): TaskResponse {
            return TaskResponse(
                id = task.id,
                orderIndex = task.orderIndex,
                title = task.title,
                description = task.description,
                dateLimit = task.dateLimit,
                grouperId = task.grouperId,
                priorityId = task.priorityId
            )
        }
    }
}

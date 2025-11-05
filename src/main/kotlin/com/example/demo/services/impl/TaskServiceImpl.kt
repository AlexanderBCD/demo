package com.example.demo.services.impl

import com.example.demo.dto.TaskRequest
import com.example.demo.dto.TaskResponse
import com.example.demo.model.Tasks
import com.example.demo.repository.TaskRepository
import com.example.demo.services.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TaskServiceImpl @Autowired constructor(
    private val taskRepository: TaskRepository
) : TaskService {

    override fun getAllTasks(): List<TaskResponse> {
        return taskRepository.getAll().map { TaskResponse.fromEntity(it) }
    }

    override fun getTaskById(id: Long): TaskResponse? {
        return taskRepository.getById(id)?.let { TaskResponse.fromEntity(it) }
    }

    override fun createTask(taskRequest: TaskRequest): TaskResponse {
        val task = Tasks(
            orderIndex = taskRequest.orderIndex,
            title = taskRequest.title,
            description = taskRequest.description,
            dateLimit = taskRequest.dateLimit,
            grouperId = taskRequest.grouperId,
            priorityId = taskRequest.priorityId
        )
        val createdTask = taskRepository.create(task)
        return TaskResponse.fromEntity(createdTask)
    }

    override fun updateTask(id: Long, taskRequest: TaskRequest): TaskResponse? {
        val existingTask = taskRepository.getById(id) ?: return null
        
        val updatedTask = existingTask.copy(
            orderIndex = taskRequest.orderIndex,
            title = taskRequest.title,
            description = taskRequest.description,
            dateLimit = taskRequest.dateLimit,
            grouperId = taskRequest.grouperId,
            priorityId = taskRequest.priorityId
        )
        
        val rowsUpdated = taskRepository.update(updatedTask)
        return if (rowsUpdated > 0) TaskResponse.fromEntity(updatedTask) else null
    }

    override fun deleteTask(id: Long): Boolean {
        return taskRepository.delete(id)
    }
}

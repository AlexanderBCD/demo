package com.example.demo.services.impl

import com.example.demo.dto.TaskRequest
import com.example.demo.dto.TaskResponse
import com.example.demo.dto.TaskWithDetails
import com.example.demo.model.Tasks
import com.example.demo.repository.TaskRepository
import com.example.demo.services.TaskService
import com.example.demo.strategy.context.TaskSortingContext
import com.example.demo.strategy.context.TaskValidationContext
import com.example.demo.strategy.sorting.TaskSortingStrategy
import com.example.demo.strategy.validation.TaskValidationStrategy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TaskServiceImpl @Autowired constructor(
    private val taskRepository: TaskRepository,
    private val validationContext: TaskValidationContext,
    private val sortingContext: TaskSortingContext
) : TaskService {

    override fun getAllTasks(): List<TaskResponse> {
        return taskRepository.getAll().map { TaskResponse.fromEntity(it) }
    }

    override fun getAllTasksWithDetails(): List<TaskWithDetails> {
        return taskRepository.getAllWithDetails()
    }

    override fun getAllTasksSorted(): List<TaskResponse> {
        val tasks = taskRepository.getAll().map { TaskResponse.fromEntity(it) }
        return sortingContext.sort(tasks)
    }

    override fun getTaskById(id: Long): TaskResponse? {
        return taskRepository.getById(id)?.let { TaskResponse.fromEntity(it) }
    }

    override fun createTask(taskRequest: TaskRequest): TaskResponse {
        // Usar estrategia de validación
        val validationResult = validationContext.validate(taskRequest)
        if (!validationResult.isValid) {
            throw IllegalArgumentException("Errores de validación: ${validationResult.errors.joinToString(", ")}")
        }

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
        // Usar estrategia de validación
        val validationResult = validationContext.validate(taskRequest)
        if (!validationResult.isValid) {
            throw IllegalArgumentException("Errores de validación: ${validationResult.errors.joinToString(", ")}")
        }

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

    // Implementación de métodos para cambiar estrategias
    override fun setValidationStrategy(strategy: TaskValidationStrategy) {
        validationContext.setStrategy(strategy)
    }

    override fun setSortingStrategy(strategy: TaskSortingStrategy) {
        sortingContext.setStrategy(strategy)
    }

    override fun getCurrentValidationStrategy(): String {
        return validationContext.getCurrentStrategyName()
    }

    override fun getCurrentSortingStrategy(): String {
        return sortingContext.getCurrentStrategyName()
    }
}

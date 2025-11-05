package com.example.demo.services

import com.example.demo.dto.TaskRequest
import com.example.demo.dto.TaskResponse
import com.example.demo.dto.TaskWithDetails
import com.example.demo.strategy.sorting.TaskSortingStrategy
import com.example.demo.strategy.validation.TaskValidationStrategy

interface TaskService {
    fun getAllTasks(): List<TaskResponse>
    fun getAllTasksWithDetails(): List<TaskWithDetails>
    fun getAllTasksSorted(): List<TaskResponse>
    fun getTaskById(id: Long): TaskResponse?
    fun createTask(taskRequest: TaskRequest): TaskResponse
    fun updateTask(id: Long, taskRequest: TaskRequest): TaskResponse?
    fun deleteTask(id: Long): Boolean
    
    // Métodos para cambiar estrategias dinámicamente
    fun setValidationStrategy(strategy: TaskValidationStrategy)
    fun setSortingStrategy(strategy: TaskSortingStrategy)
    fun getCurrentValidationStrategy(): String
    fun getCurrentSortingStrategy(): String
}
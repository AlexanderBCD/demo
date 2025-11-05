package com.example.demo.services

import com.example.demo.dto.TaskRequest
import com.example.demo.dto.TaskResponse

interface TaskService {
    fun getAllTasks(): List<TaskResponse>
    fun getTaskById(id: Long): TaskResponse?
    fun createTask(taskRequest: TaskRequest): TaskResponse
    fun updateTask(id: Long, taskRequest: TaskRequest): TaskResponse?
    fun deleteTask(id: Long): Boolean
}
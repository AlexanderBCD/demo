package com.example.demo.repository
import com.example.demo.dto.TaskWithDetails
import com.example.demo.model.Tasks

interface TaskRepository {
    fun getAll(): List<Tasks>
    fun getAllWithDetails(): List<TaskWithDetails>
    fun getById(id: Long): Tasks?
    fun create(tasks: Tasks): Tasks
    fun update(tasks: Tasks): Int
    fun delete(id: Long): Boolean
}
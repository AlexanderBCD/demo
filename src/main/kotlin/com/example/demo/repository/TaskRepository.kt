package com.example.demo.repository
import com.example.demo.model.Tasks

interface TaskRepository {
    fun getAll(): List<Tasks>
    fun getById(id: Long): Tasks?
    fun create(tasks: Tasks): Tasks
    fun update(tasks: Tasks): Int
    fun delete(id: Long): Boolean
}
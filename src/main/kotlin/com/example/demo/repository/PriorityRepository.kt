package com.example.demo.repository

import com.example.demo.model.Priorities

interface PriorityRepository {
    fun getAll(): List<Priorities>
    fun getById(id: Long): Priorities?
    fun create(priority: Priorities): Priorities
    fun update(priority: Priorities): Int
    fun delete(id: Long): Boolean
}

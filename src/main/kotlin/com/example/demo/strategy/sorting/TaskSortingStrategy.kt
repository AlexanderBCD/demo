package com.example.demo.strategy.sorting

import com.example.demo.dto.TaskResponse

/**
 * Strategy Pattern: Interfaz que define el contrato para ordenar tareas
 */
interface TaskSortingStrategy {
    fun sort(tasks: List<TaskResponse>): List<TaskResponse>
    fun getStrategyName(): String
}

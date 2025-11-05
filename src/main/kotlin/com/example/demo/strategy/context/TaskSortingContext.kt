package com.example.demo.strategy.context

import com.example.demo.dto.TaskResponse
import com.example.demo.strategy.sorting.TaskSortingStrategy
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Context del Patrón Strategy para ordenamiento de tareas
 * Permite cambiar dinámicamente la estrategia de ordenamiento
 */
@Component
class TaskSortingContext(
    @Qualifier("orderIndexSorting") private var currentStrategy: TaskSortingStrategy
) {

    fun setStrategy(strategy: TaskSortingStrategy) {
        this.currentStrategy = strategy
    }

    fun sort(tasks: List<TaskResponse>): List<TaskResponse> {
        return currentStrategy.sort(tasks)
    }

    fun getCurrentStrategyName(): String {
        return currentStrategy.getStrategyName()
    }
}

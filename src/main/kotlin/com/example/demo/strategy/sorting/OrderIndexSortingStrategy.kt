package com.example.demo.strategy.sorting

import com.example.demo.dto.TaskResponse
import org.springframework.stereotype.Component

/**
 * Estrategia de ordenamiento por índice de orden
 */
@Component("orderIndexSorting")
class OrderIndexSortingStrategy : TaskSortingStrategy {

    override fun sort(tasks: List<TaskResponse>): List<TaskResponse> {
        return tasks.sortedBy { it.orderIndex }
    }

    override fun getStrategyName(): String = "Ordenar por Índice"
}

package com.example.demo.strategy.sorting

import com.example.demo.dto.TaskResponse
import org.springframework.stereotype.Component

/**
 * Estrategia de ordenamiento por prioridad (IDs más altos = mayor prioridad)
 */
@Component("prioritySorting")
class PrioritySortingStrategy : TaskSortingStrategy {

    override fun sort(tasks: List<TaskResponse>): List<TaskResponse> {
        return tasks.sortedWith(compareByDescending(
            nullsLast<Long>()
        ) { it.priorityId })
    }

    override fun getStrategyName(): String = "Ordenar por Prioridad"
}

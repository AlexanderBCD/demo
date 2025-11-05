package com.example.demo.strategy.sorting

import com.example.demo.dto.TaskResponse
import org.springframework.stereotype.Component

/**
 * Estrategia de ordenamiento por título alfabéticamente
 */
@Component("titleSorting")
class TitleSortingStrategy : TaskSortingStrategy {

    override fun sort(tasks: List<TaskResponse>): List<TaskResponse> {
        return tasks.sortedBy { it.title.lowercase() }
    }

    override fun getStrategyName(): String = "Ordenar por Título"
}

package com.example.demo.strategy.sorting

import com.example.demo.dto.TaskResponse
import org.springframework.stereotype.Component
import java.util.*

/**
 * Estrategia de ordenamiento por fecha límite (más próximas primero)
 */
@Component("dateLimitSorting")
class DateLimitSortingStrategy : TaskSortingStrategy {

    override fun sort(tasks: List<TaskResponse>): List<TaskResponse> {
        return tasks.sortedWith(compareBy(
            nullsLast<Date>()
        ) { it.dateLimit })
    }

    override fun getStrategyName(): String = "Ordenar por Fecha Límite"
}

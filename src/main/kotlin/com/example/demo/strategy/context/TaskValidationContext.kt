package com.example.demo.strategy.context

import com.example.demo.dto.TaskRequest
import com.example.demo.strategy.validation.TaskValidationStrategy
import com.example.demo.strategy.validation.ValidationResult
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Context del Patrón Strategy para validación de tareas
 * Permite cambiar dinámicamente la estrategia de validación
 */
@Component
class TaskValidationContext(
    @Qualifier("strictValidation") private var currentStrategy: TaskValidationStrategy
) {

    fun setStrategy(strategy: TaskValidationStrategy) {
        this.currentStrategy = strategy
    }

    fun validate(taskRequest: TaskRequest): ValidationResult {
        return currentStrategy.validate(taskRequest)
    }

    fun getCurrentStrategyName(): String {
        return currentStrategy.getStrategyName()
    }
}

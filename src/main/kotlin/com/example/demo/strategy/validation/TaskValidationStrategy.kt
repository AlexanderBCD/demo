package com.example.demo.strategy.validation

import com.example.demo.dto.TaskRequest

/**
 * Strategy Pattern: Interfaz que define el contrato para validar tareas
 */
interface TaskValidationStrategy {
    fun validate(taskRequest: TaskRequest): ValidationResult
    fun getStrategyName(): String
}

data class ValidationResult(
    val isValid: Boolean,
    val errors: List<String> = emptyList()
)

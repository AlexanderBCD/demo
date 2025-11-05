package com.example.demo.strategy.validation

import com.example.demo.dto.TaskRequest
import org.springframework.stereotype.Component

/**
 * Estrategia de validación básica: solo valida campos obligatorios
 */
@Component("basicValidation")
class BasicTaskValidationStrategy : TaskValidationStrategy {

    override fun validate(taskRequest: TaskRequest): ValidationResult {
        val errors = mutableListOf<String>()

        if (taskRequest.title.isBlank()) {
            errors.add("El título es obligatorio")
        }

        if (taskRequest.orderIndex < 0) {
            errors.add("El índice de orden debe ser mayor o igual a 0")
        }

        return ValidationResult(
            isValid = errors.isEmpty(),
            errors = errors
        )
    }

    override fun getStrategyName(): String = "Validación Básica"
}

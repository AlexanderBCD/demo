package com.example.demo.strategy.validation

import com.example.demo.dto.TaskRequest
import org.springframework.stereotype.Component

/**
 * Estrategia de validación permisiva: solo valida lo mínimo indispensable
 */
@Component("permissiveValidation")
class PermissiveTaskValidationStrategy : TaskValidationStrategy {

    override fun validate(taskRequest: TaskRequest): ValidationResult {
        val errors = mutableListOf<String>()

        // Solo validamos que el título no esté completamente vacío
        if (taskRequest.title.isBlank()) {
            errors.add("El título no puede estar vacío")
        }

        return ValidationResult(
            isValid = errors.isEmpty(),
            errors = errors
        )
    }

    override fun getStrategyName(): String = "Validación Permisiva"
}

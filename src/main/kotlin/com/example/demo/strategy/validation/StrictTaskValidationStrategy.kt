package com.example.demo.strategy.validation

import com.example.demo.dto.TaskRequest
import org.springframework.stereotype.Component
import java.util.*

/**
 * Estrategia de validación estricta: valida campos obligatorios + reglas de negocio
 */
@Component("strictValidation")
class StrictTaskValidationStrategy : TaskValidationStrategy {

    override fun validate(taskRequest: TaskRequest): ValidationResult {
        val errors = mutableListOf<String>()

        // Validación de título
        if (taskRequest.title.isBlank()) {
            errors.add("El título es obligatorio")
        } else if (taskRequest.title.length < 3) {
            errors.add("El título debe tener al menos 3 caracteres")
        } else if (taskRequest.title.length > 50) {
            errors.add("El título no puede exceder 50 caracteres")
        }

        // Validación de orden
        if (taskRequest.orderIndex < 0) {
            errors.add("El índice de orden debe ser mayor o igual a 0")
        }

        // Validación de descripción
        if (taskRequest.description != null && taskRequest.description.length > 1000) {
            errors.add("La descripción no puede exceder 1000 caracteres")
        }

        // Validación de fecha límite
        if (taskRequest.dateLimit != null) {
            val now = Date()
            if (taskRequest.dateLimit.before(now)) {
                errors.add("La fecha límite no puede ser anterior a la fecha actual")
            }
        }

        // Validación de prioridad y grupo
        if (taskRequest.priorityId != null && taskRequest.priorityId <= 0) {
            errors.add("El ID de prioridad debe ser mayor a 0")
        }

        if (taskRequest.grouperId != null && taskRequest.grouperId <= 0) {
            errors.add("El ID de grupo debe ser mayor a 0")
        }

        return ValidationResult(
            isValid = errors.isEmpty(),
            errors = errors
        )
    }

    override fun getStrategyName(): String = "Validación Estricta"
}

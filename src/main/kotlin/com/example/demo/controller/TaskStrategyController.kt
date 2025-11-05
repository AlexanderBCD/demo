package com.example.demo.controller

import com.example.demo.dto.TaskResponse
import com.example.demo.services.TaskService
import com.example.demo.strategy.sorting.*
import com.example.demo.strategy.validation.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Controlador REST para demostrar el uso del Patrón Strategy
 * Permite cambiar dinámicamente las estrategias de validación y ordenamiento
 */
@RestController
@RequestMapping("/api/tasks/strategy")
class TaskStrategyController @Autowired constructor(
    private val taskService: TaskService,
    private val basicValidation: BasicTaskValidationStrategy,
    private val strictValidation: StrictTaskValidationStrategy,
    private val permissiveValidation: PermissiveTaskValidationStrategy,
    private val orderIndexSorting: OrderIndexSortingStrategy,
    private val titleSorting: TitleSortingStrategy,
    private val dateLimitSorting: DateLimitSortingStrategy,
    private val prioritySorting: PrioritySortingStrategy
) {

    @GetMapping("/validation/current")
    fun getCurrentValidationStrategy(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(mapOf(
            "currentStrategy" to taskService.getCurrentValidationStrategy()
        ))
    }

    @GetMapping("/sorting/current")
    fun getCurrentSortingStrategy(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(mapOf(
            "currentStrategy" to taskService.getCurrentSortingStrategy()
        ))
    }

    @PostMapping("/validation/set/{strategy}")
    fun setValidationStrategy(@PathVariable strategy: String): ResponseEntity<Map<String, String>> {
        val selectedStrategy = when (strategy.lowercase()) {
            "basic" -> basicValidation
            "strict" -> strictValidation
            "permissive" -> permissiveValidation
            else -> return ResponseEntity.badRequest().body(mapOf(
                "error" to "Estrategia no válida. Opciones: basic, strict, permissive"
            ))
        }
        
        taskService.setValidationStrategy(selectedStrategy)
        return ResponseEntity.ok(mapOf(
            "message" to "Estrategia de validación cambiada exitosamente",
            "newStrategy" to selectedStrategy.getStrategyName()
        ))
    }

    @PostMapping("/sorting/set/{strategy}")
    fun setSortingStrategy(@PathVariable strategy: String): ResponseEntity<Map<String, String>> {
        val selectedStrategy = when (strategy.lowercase()) {
            "orderindex" -> orderIndexSorting
            "title" -> titleSorting
            "datelimit" -> dateLimitSorting
            "priority" -> prioritySorting
            else -> return ResponseEntity.badRequest().body(mapOf(
                "error" to "Estrategia no válida. Opciones: orderindex, title, datelimit, priority"
            ))
        }
        
        taskService.setSortingStrategy(selectedStrategy)
        return ResponseEntity.ok(mapOf(
            "message" to "Estrategia de ordenamiento cambiada exitosamente",
            "newStrategy" to selectedStrategy.getStrategyName()
        ))
    }

    @GetMapping("/sorted")
    fun getTasksSorted(): ResponseEntity<Map<String, Any>> {
        val sortedTasks = taskService.getAllTasksSorted()
        return ResponseEntity.ok(mapOf(
            "strategy" to taskService.getCurrentSortingStrategy(),
            "tasks" to sortedTasks
        ))
    }

    @GetMapping("/strategies/validation")
    fun getAvailableValidationStrategies(): ResponseEntity<Map<String, List<String>>> {
        return ResponseEntity.ok(mapOf(
            "available" to listOf(
                "basic - ${basicValidation.getStrategyName()}",
                "strict - ${strictValidation.getStrategyName()}",
                "permissive - ${permissiveValidation.getStrategyName()}"
            )
        ))
    }

    @GetMapping("/strategies/sorting")
    fun getAvailableSortingStrategies(): ResponseEntity<Map<String, List<String>>> {
        return ResponseEntity.ok(mapOf(
            "available" to listOf(
                "orderindex - ${orderIndexSorting.getStrategyName()}",
                "title - ${titleSorting.getStrategyName()}",
                "datelimit - ${dateLimitSorting.getStrategyName()}",
                "priority - ${prioritySorting.getStrategyName()}"
            )
        ))
    }
}

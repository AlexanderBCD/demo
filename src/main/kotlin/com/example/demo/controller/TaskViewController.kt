package com.example.demo.controller

import com.example.demo.dto.TaskRequest
import com.example.demo.services.TaskService
import com.example.demo.services.GrouperService
import com.example.demo.services.PriorityService
import com.example.demo.strategy.sorting.*
import com.example.demo.strategy.validation.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.text.SimpleDateFormat
import java.util.*

@Controller
@RequestMapping("/tasks")
class TaskViewController @Autowired constructor(
    private val taskService: TaskService,
    private val grouperService: GrouperService,
    private val priorityService: PriorityService,
    private val orderIndexSorting: OrderIndexSortingStrategy,
    private val titleSorting: TitleSortingStrategy,
    private val dateLimitSorting: DateLimitSortingStrategy,
    private val prioritySorting: PrioritySortingStrategy,
    private val basicValidation: BasicTaskValidationStrategy,
    private val strictValidation: StrictTaskValidationStrategy,
    private val permissiveValidation: PermissiveTaskValidationStrategy
) {

    @GetMapping
    fun listTasks(model: Model, @RequestParam(required = false) sortStrategy: String?): String {
        // Cambiar estrategia si se proporciona
        if (sortStrategy != null) {
            when (sortStrategy.lowercase()) {
                "orderindex" -> taskService.setSortingStrategy(orderIndexSorting)
                "title" -> taskService.setSortingStrategy(titleSorting)
                "datelimit" -> taskService.setSortingStrategy(dateLimitSorting)
                "priority" -> taskService.setSortingStrategy(prioritySorting)
            }
        }
        
        // Obtener tareas con detalles (JOIN con groupers y priorities)
        val tasksWithDetails = taskService.getAllTasksWithDetails()
        model.addAttribute("tasks", tasksWithDetails)
        model.addAttribute("currentSortingStrategy", taskService.getCurrentSortingStrategy())
        model.addAttribute("currentValidationStrategy", taskService.getCurrentValidationStrategy())
        return "tasks/list"
    }

    @GetMapping("/validation")
    fun changeValidationStrategy(
        @RequestParam strategy: String,
        redirectAttributes: RedirectAttributes
    ): String {
        try {
            when (strategy.lowercase()) {
                "basic" -> taskService.setValidationStrategy(basicValidation)
                "strict" -> taskService.setValidationStrategy(strictValidation)
                "permissive" -> taskService.setValidationStrategy(permissiveValidation)
                else -> throw IllegalArgumentException("Estrategia de validación no válida")
            }
            redirectAttributes.addFlashAttribute("message", "Estrategia de validación cambiada a: $strategy")
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Error al cambiar estrategia: ${e.message}")
        }
        return "redirect:/tasks"
    }

    @GetMapping("/{id}")
    fun viewTask(@PathVariable id: Long, model: Model, redirectAttributes: RedirectAttributes): String {
        val task = taskService.getTaskById(id)
        return if (task != null) {
            model.addAttribute("task", task)
            "tasks/view"
        } else {
            redirectAttributes.addFlashAttribute("error", "Tarea no encontrada")
            "redirect:/tasks"
        }
    }

    @GetMapping("/new")
    fun newTaskForm(model: Model): String {
        model.addAttribute("task", TaskRequest(
            orderIndex = 0,
            title = "",
            description = null,
            dateLimit = null,
            grouperId = null,
            priorityId = null
        ))
        model.addAttribute("groupers", grouperService.getAllGroupers())
        model.addAttribute("priorities", priorityService.getAllPriorities())
        return "tasks/form"
    }

    @PostMapping
    fun createTask(
        @ModelAttribute task: TaskRequest,
        redirectAttributes: RedirectAttributes
    ): String {
        return try {
            taskService.createTask(task)
            redirectAttributes.addFlashAttribute("message", "Tarea creada exitosamente")
            "redirect:/tasks"
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la tarea: ${e.message}")
            "redirect:/tasks/new"
        }
    }

    @GetMapping("/edit/{id}")
    fun editTaskForm(@PathVariable id: Long, model: Model, redirectAttributes: RedirectAttributes): String {
        val task = taskService.getTaskById(id)
        return if (task != null) {
            // Convertir TaskResponse a TaskRequest para el formulario
            val taskRequest = TaskRequest(
                orderIndex = task.orderIndex,
                title = task.title,
                description = task.description,
                dateLimit = task.dateLimit,
                grouperId = task.grouperId,
                priorityId = task.priorityId
            )
            model.addAttribute("task", taskRequest)
            model.addAttribute("groupers", grouperService.getAllGroupers())
            model.addAttribute("priorities", priorityService.getAllPriorities())
            "tasks/edit"
        } else {
            redirectAttributes.addFlashAttribute("error", "Tarea no encontrada")
            "redirect:/tasks"
        }
    }

    @PostMapping("/{id}")
    fun updateTask(
        @PathVariable id: Long,
        @ModelAttribute taskRequest: TaskRequest,
        redirectAttributes: RedirectAttributes
    ): String {
        return try {
            val updated = taskService.updateTask(id, taskRequest)
            if (updated != null) {
                redirectAttributes.addFlashAttribute("message", "Tarea actualizada exitosamente")
                "redirect:/tasks"
            } else {
                redirectAttributes.addFlashAttribute("error", "No se pudo actualizar la tarea")
                "redirect:/tasks/edit/$id"
            }
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la tarea: ${e.message}")
            "redirect:/tasks/edit/$id"
        }
    }

    @PostMapping("/delete/{id}")
    fun deleteTask(@PathVariable id: Long, redirectAttributes: RedirectAttributes): String {
        return try {
            val deleted = taskService.deleteTask(id)
            if (deleted) {
                redirectAttributes.addFlashAttribute("message", "Tarea eliminada exitosamente")
            } else {
                redirectAttributes.addFlashAttribute("error", "No se pudo eliminar la tarea")
            }
            "redirect:/tasks"
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la tarea: ${e.message}")
            "redirect:/tasks"
        }
    }
}

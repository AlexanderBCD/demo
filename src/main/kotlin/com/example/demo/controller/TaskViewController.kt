package com.example.demo.controller

import com.example.demo.dto.TaskRequest
import com.example.demo.services.TaskService
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
    private val taskService: TaskService
) {

    @GetMapping
    fun listTasks(model: Model): String {
        val tasks = taskService.getAllTasks()
        model.addAttribute("tasks", tasks)
        return "tasks/list"
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
            model.addAttribute("task", task)
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

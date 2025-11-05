package com.example.demo.controller

import com.example.demo.dto.PriorityRequest
import com.example.demo.services.PriorityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/priorities")
class PriorityViewController @Autowired constructor(
    private val priorityService: PriorityService
) {

    @GetMapping
    fun listPriorities(model: Model): String {
        val priorities = priorityService.getAllPriorities()
        model.addAttribute("priorities", priorities)
        return "priorities/list"
    }

    @GetMapping("/{id}")
    fun viewPriority(@PathVariable id: Long, model: Model, redirectAttributes: RedirectAttributes): String {
        val priority = priorityService.getPriorityById(id)
        return if (priority != null) {
            model.addAttribute("priority", priority)
            "priorities/view"
        } else {
            redirectAttributes.addFlashAttribute("error", "Prioridad no encontrada")
            "redirect:/priorities"
        }
    }

    @GetMapping("/new")
    fun newPriorityForm(model: Model): String {
        model.addAttribute("priority", PriorityRequest(name = ""))
        return "priorities/form"
    }

    @PostMapping
    fun createPriority(
        @ModelAttribute priority: PriorityRequest,
        redirectAttributes: RedirectAttributes
    ): String {
        return try {
            priorityService.createPriority(priority)
            redirectAttributes.addFlashAttribute("message", "Prioridad creada exitosamente")
            "redirect:/priorities"
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la prioridad: ${e.message}")
            "redirect:/priorities/new"
        }
    }

    @GetMapping("/edit/{id}")
    fun editPriorityForm(@PathVariable id: Long, model: Model, redirectAttributes: RedirectAttributes): String {
        val priority = priorityService.getPriorityById(id)
        return if (priority != null) {
            model.addAttribute("priority", priority)
            "priorities/edit"
        } else {
            redirectAttributes.addFlashAttribute("error", "Prioridad no encontrada")
            "redirect:/priorities"
        }
    }

    @PostMapping("/{id}")
    fun updatePriority(
        @PathVariable id: Long,
        @ModelAttribute priorityRequest: PriorityRequest,
        redirectAttributes: RedirectAttributes
    ): String {
        return try {
            val updated = priorityService.updatePriority(id, priorityRequest)
            if (updated != null) {
                redirectAttributes.addFlashAttribute("message", "Prioridad actualizada exitosamente")
                "redirect:/priorities"
            } else {
                redirectAttributes.addFlashAttribute("error", "No se pudo actualizar la prioridad")
                "redirect:/priorities/edit/$id"
            }
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la prioridad: ${e.message}")
            "redirect:/priorities/edit/$id"
        }
    }

    @PostMapping("/delete/{id}")
    fun deletePriority(@PathVariable id: Long, redirectAttributes: RedirectAttributes): String {
        return try {
            val deleted = priorityService.deletePriority(id)
            if (deleted) {
                redirectAttributes.addFlashAttribute("message", "Prioridad eliminada exitosamente")
            } else {
                redirectAttributes.addFlashAttribute("error", "No se pudo eliminar la prioridad")
            }
            "redirect:/priorities"
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la prioridad: ${e.message}")
            "redirect:/priorities"
        }
    }
}

package com.example.demo.controller

import com.example.demo.dto.GrouperRequest
import com.example.demo.services.GrouperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/groupers")
class GrouperViewController @Autowired constructor(
    private val grouperService: GrouperService
) {

    @GetMapping
    fun listGroupers(model: Model): String {
        val groupers = grouperService.getAllGroupers()
        model.addAttribute("groupers", groupers)
        return "groupers/list"
    }

    @GetMapping("/{id}")
    fun viewGrouper(@PathVariable id: Long, model: Model, redirectAttributes: RedirectAttributes): String {
        val grouper = grouperService.getGrouperById(id)
        return if (grouper != null) {
            model.addAttribute("grouper", grouper)
            "groupers/view"
        } else {
            redirectAttributes.addFlashAttribute("error", "Grupo no encontrado")
            "redirect:/groupers"
        }
    }

    @GetMapping("/new")
    fun newGrouperForm(model: Model): String {
        model.addAttribute("grouper", GrouperRequest(name = ""))
        return "groupers/form"
    }

    @PostMapping
    fun createGrouper(
        @ModelAttribute grouper: GrouperRequest,
        redirectAttributes: RedirectAttributes
    ): String {
        return try {
            grouperService.createGrouper(grouper)
            redirectAttributes.addFlashAttribute("message", "Grupo creado exitosamente")
            "redirect:/groupers"
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el grupo: ${e.message}")
            "redirect:/groupers/new"
        }
    }

    @GetMapping("/edit/{id}")
    fun editGrouperForm(@PathVariable id: Long, model: Model, redirectAttributes: RedirectAttributes): String {
        val grouper = grouperService.getGrouperById(id)
        return if (grouper != null) {
            model.addAttribute("grouper", grouper)
            "groupers/edit"
        } else {
            redirectAttributes.addFlashAttribute("error", "Grupo no encontrado")
            "redirect:/groupers"
        }
    }

    @PostMapping("/{id}")
    fun updateGrouper(
        @PathVariable id: Long,
        @ModelAttribute grouperRequest: GrouperRequest,
        redirectAttributes: RedirectAttributes
    ): String {
        return try {
            val updated = grouperService.updateGrouper(id, grouperRequest)
            if (updated != null) {
                redirectAttributes.addFlashAttribute("message", "Grupo actualizado exitosamente")
                "redirect:/groupers"
            } else {
                redirectAttributes.addFlashAttribute("error", "No se pudo actualizar el grupo")
                "redirect:/groupers/edit/$id"
            }
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el grupo: ${e.message}")
            "redirect:/groupers/edit/$id"
        }
    }

    @PostMapping("/delete/{id}")
    fun deleteGrouper(@PathVariable id: Long, redirectAttributes: RedirectAttributes): String {
        return try {
            val deleted = grouperService.deleteGrouper(id)
            if (deleted) {
                redirectAttributes.addFlashAttribute("message", "Grupo eliminado exitosamente")
            } else {
                redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el grupo")
            }
            "redirect:/groupers"
        } catch (e: Exception) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el grupo: ${e.message}")
            "redirect:/groupers"
        }
    }
}

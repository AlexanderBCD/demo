package com.example.demo.dto

import com.example.demo.model.Priorities

data class PriorityResponse(
    val id: Long,
    val name: String
) {
    companion object {
        fun fromEntity(priority: Priorities): PriorityResponse {
            return PriorityResponse(
                id = priority.id,
                name = priority.name
            )
        }
    }
}

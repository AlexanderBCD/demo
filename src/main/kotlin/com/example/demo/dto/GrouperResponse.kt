package com.example.demo.dto

import com.example.demo.model.Groupers

data class GrouperResponse(
    val id: Long,
    val name: String
) {
    companion object {
        fun fromEntity(grouper: Groupers): GrouperResponse {
            return GrouperResponse(
                id = grouper.id,
                name = grouper.name
            )
        }
    }
}

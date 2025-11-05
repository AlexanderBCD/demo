package com.example.demo.dto

import java.util.*

data class TaskWithDetails(
    val id: Long,
    val orderIndex: Int,
    val title: String,
    val description: String? = null,
    val dateLimit: Date? = null,
    val grouperId: Long? = null,
    val grouperName: String? = null,
    val priorityId: Long? = null,
    val priorityName: String? = null
)

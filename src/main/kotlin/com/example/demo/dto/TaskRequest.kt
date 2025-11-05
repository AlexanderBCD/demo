package com.example.demo.dto

import java.util.*

data class TaskRequest(
    val orderIndex: Int,
    val title: String,
    val description: String? = null,
    val dateLimit: Date? = null,
    val grouperId: Long? = null,
    val priorityId: Long? = null
)

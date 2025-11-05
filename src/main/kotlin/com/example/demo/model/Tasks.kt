package com.example.demo.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "tasks")
data class Tasks(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "order_index", nullable = false, unique = true)
    val orderIndex: Int,

    @Column(nullable = false, length = 50)
    val title: String,

    @Column(columnDefinition = "MEDIUMTEXT")
    val description: String? = null,

    @Column(name = "date_limit")
    @Temporal(TemporalType.TIMESTAMP)
    val dateLimit: Date? = null,

    @Column(name = "grouper_id")
    val grouperId: Long? = null,

    @Column(name = "priority_id")
    val priorityId: Long? = null
)



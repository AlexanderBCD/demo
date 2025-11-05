package com.example.demo.model
import jakarta.persistence.*

@Entity
@Table(name = "priorities")

data class Priorities(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 50)
    val name: String

)

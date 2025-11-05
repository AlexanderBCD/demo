package com.example.demo.model
import jakarta.persistence.*

@Entity
@Table(name = "groupers")
data class Groupers(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 50, unique = true)
    val name: String
)

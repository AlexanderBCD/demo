package com.example.demo.repository

import com.example.demo.model.Groupers

interface GrouperRepository {
    fun getAll(): List<Groupers>
    fun getById(id: Long): Groupers?
    fun create(grouper: Groupers): Groupers
    fun update(grouper: Groupers): Int
    fun delete(id: Long): Boolean
}

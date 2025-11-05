package com.example.demo.repository.impl

import com.example.demo.model.Priorities
import com.example.demo.repository.PriorityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import java.sql.Statement

@Repository
class PriorityRepositoryImpl @Autowired constructor(private val jdbcTemplate: JdbcTemplate) : PriorityRepository {

    private val priorityRowMapper = RowMapper { rs, _ ->
        Priorities(
            id = rs.getLong("id"),
            name = rs.getString("name")
        )
    }

    override fun getAll(): List<Priorities> {
        val sql = "SELECT * FROM priorities ORDER BY name"
        return jdbcTemplate.query(sql, priorityRowMapper)
    }

    override fun getById(id: Long): Priorities? {
        val sql = "SELECT * FROM priorities WHERE id = ?"
        return jdbcTemplate.query(sql, priorityRowMapper, id).firstOrNull()
    }

    override fun create(priority: Priorities): Priorities {
        val sql = "INSERT INTO priorities (name) VALUES (?)"
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        
        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ps.setString(1, priority.name)
            ps
        }, keyHolder)
        
        val generatedId = keyHolder.key?.toLong() ?: priority.id
        return priority.copy(id = generatedId)
    }

    override fun update(priority: Priorities): Int {
        val sql = "UPDATE priorities SET name = ? WHERE id = ?"
        return jdbcTemplate.update(sql, priority.name, priority.id)
    }

    override fun delete(id: Long): Boolean {
        val sql = "DELETE FROM priorities WHERE id = ?"
        val rowsDeleted = jdbcTemplate.update(sql, id)
        return rowsDeleted > 0
    }
}

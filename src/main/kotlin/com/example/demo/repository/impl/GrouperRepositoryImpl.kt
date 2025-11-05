package com.example.demo.repository.impl

import com.example.demo.model.Groupers
import com.example.demo.repository.GrouperRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import java.sql.Statement

@Repository
class GrouperRepositoryImpl @Autowired constructor(private val jdbcTemplate: JdbcTemplate) : GrouperRepository {

    private val grouperRowMapper = RowMapper { rs, _ ->
        Groupers(
            id = rs.getLong("id"),
            name = rs.getString("name")
        )
    }

    override fun getAll(): List<Groupers> {
        val sql = "SELECT * FROM groupers ORDER BY name"
        return jdbcTemplate.query(sql, grouperRowMapper)
    }

    override fun getById(id: Long): Groupers? {
        val sql = "SELECT * FROM groupers WHERE id = ?"
        return jdbcTemplate.query(sql, grouperRowMapper, id).firstOrNull()
    }

    override fun create(grouper: Groupers): Groupers {
        val sql = "INSERT INTO groupers (name) VALUES (?)"
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        
        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ps.setString(1, grouper.name)
            ps
        }, keyHolder)
        
        val generatedId = keyHolder.key?.toLong() ?: grouper.id
        return grouper.copy(id = generatedId)
    }

    override fun update(grouper: Groupers): Int {
        val sql = "UPDATE groupers SET name = ? WHERE id = ?"
        return jdbcTemplate.update(sql, grouper.name, grouper.id)
    }

    override fun delete(id: Long): Boolean {
        val sql = "DELETE FROM groupers WHERE id = ?"
        val rowsDeleted = jdbcTemplate.update(sql, id)
        return rowsDeleted > 0
    }
}

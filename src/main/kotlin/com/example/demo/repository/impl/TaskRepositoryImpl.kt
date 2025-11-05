package com.example.demo.repository.impl

import com.example.demo.model.Tasks
import com.example.demo.repository.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import java.sql.Statement

@Repository
class TaskRepositoryImpl @Autowired constructor(private val jdbcTemplate: JdbcTemplate) : TaskRepository {

    private val taskRowMapper = RowMapper { rs, _ ->
        Tasks(
            id = rs.getLong("id"),
            orderIndex = rs.getInt("order_index"),
            title = rs.getString("title"),
            description = rs.getString("description"),
            dateLimit = rs.getTimestamp("date_limit"),
            grouperId = rs.getLong("grouper_id").takeIf { !rs.wasNull() },
            priorityId = rs.getLong("priority_id").takeIf { !rs.wasNull() }
        )
    }

    override fun getAll(): List<Tasks> {
        val sql = "SELECT * FROM tasks ORDER BY order_index"
        return jdbcTemplate.query(sql, taskRowMapper)
    }

    override fun getById(id: Long): Tasks? {
        val sql = "SELECT * FROM tasks WHERE id = ?"
        return jdbcTemplate.query(sql, taskRowMapper, id).firstOrNull()
    }

    override fun create(tasks: Tasks): Tasks {
        val sql = """
            INSERT INTO tasks (order_index, title, description, date_limit, grouper_id, priority_id) 
            VALUES (?, ?, ?, ?, ?, ?)
        """
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        
        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ps.setInt(1, tasks.orderIndex)
            ps.setString(2, tasks.title)
            ps.setString(3, tasks.description)
            ps.setTimestamp(4, tasks.dateLimit?.let { java.sql.Timestamp(it.time) })
            if (tasks.grouperId != null) ps.setLong(5, tasks.grouperId) else ps.setNull(5, java.sql.Types.BIGINT)
            if (tasks.priorityId != null) ps.setLong(6, tasks.priorityId) else ps.setNull(6, java.sql.Types.BIGINT)
            ps
        }, keyHolder)
        
        val generatedId = keyHolder.key?.toLong() ?: tasks.id
        return tasks.copy(id = generatedId)
    }

    override fun update(tasks: Tasks): Int {
        val sql = """
            UPDATE tasks 
            SET order_index = ?, title = ?, description = ?, date_limit = ?, grouper_id = ?, priority_id = ? 
            WHERE id = ?
        """
        return jdbcTemplate.update(sql, 
            tasks.orderIndex, 
            tasks.title, 
            tasks.description, 
            tasks.dateLimit,
            tasks.grouperId,
            tasks.priorityId,
            tasks.id
        )
    }

    override fun delete(id: Long): Boolean {
        val sql = "DELETE FROM tasks WHERE id = ?"
        val rowsDeleted = jdbcTemplate.update(sql, id)
        return rowsDeleted > 0
    }
}

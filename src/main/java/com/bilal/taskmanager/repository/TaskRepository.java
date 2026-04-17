package com.bilal.taskmanager.repository;

import com.bilal.taskmanager.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    // Spring handles the "Plumbing" and injects JdbcTemplate here
    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // CREATE: Manual SQL Insert
    public int save(Task task) {
        String sql = "INSERT INTO task (title, description, completed, created_at) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
            task.getTitle(), 
            task.getDescription(), 
            task.getCompleted(), 
            task.getCreatedAt()
        );
    }

    // READ: Manual SQL Select
    public List<Task> findAll() {
        String sql = "SELECT * FROM task";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }
    
    public Task findById(Long id){
    	String sql = "SELECT * FROM task where id = ?";
    	return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), id);
    }
    
    public int update(Long id, Task task) {
    	String sql = "UPDATE task SET title= ?, description = ?, completed = ? WHERE id = ?";
    	return jdbcTemplate.update(sql,
			task.getTitle(),
			task.getDescription(),
			task.getCompleted(),
			id
			);
    }

    // The mapper Turns a Database Row into a Java Task Object
    private static final class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setTitle(rs.getString("title"));
            task.setDescription(rs.getString("description"));
            task.setCompleted(rs.getBoolean("completed"));
            task.setCreatedAt(rs.getDate("created_at").toLocalDate());
            return task;
        }
    }
}
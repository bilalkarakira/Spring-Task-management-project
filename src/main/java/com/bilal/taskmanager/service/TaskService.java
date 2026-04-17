package com.bilal.taskmanager.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bilal.taskmanager.dto.TaskRequest;
import com.bilal.taskmanager.model.Task;
import com.bilal.taskmanager.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository repository;
	
	public List<Task> getAllTasks() {
		return repository.findAll();
	}
	
	public Task getTask(Long id) {
		return repository.findById(id);
	}
	
	public Task createTask(TaskRequest request) {
		
		Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(
            request.getCompleted() != null ? request.getCompleted() : false
        );
        task.setCreatedAt(LocalDate.now());
        
		int result = repository.save(task);
		        
        if (result > 0) {
            return task; 
        } else {
            throw new RuntimeException("Could not save task to database");
        }
	}
	
	public Task updateTask(Long id, TaskRequest request) {
		Task task = new Task();
		
		task.setTitle(request.getTitle());
		task.setDescription(request.getDescription());
        task.setCompleted(
            request.getCompleted() != null ? request.getCompleted() : false
        );
        task.setUpdatedAt(LocalDate.now());
        
        int rowsAffected = repository.update(id, task);
        if (rowsAffected == 0) {
            // This is how you handle a "404" situation in JDBC Template
            throw new RuntimeException("Update failed: Task with ID " + id + " not found.");
        }
        return task;
	}
}

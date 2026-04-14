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
	
	public Task createTask(TaskRequest request) {
		
		Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(
            request.getCompleted() != null ? request.getCompleted() : false
        );
        task.setCreatedAt(LocalDate.now());
        
		return repository.save(task);
	}
}

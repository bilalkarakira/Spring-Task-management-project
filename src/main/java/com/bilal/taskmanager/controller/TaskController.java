package com.bilal.taskmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bilal.taskmanager.dto.TaskRequest;
import com.bilal.taskmanager.model.Task;
import com.bilal.taskmanager.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@GetMapping
	public List<Task> getAllTasks(){
		logger.info("Alohaaaaa");
		return taskService.getAllTasks();
	}
	
	@GetMapping("/{id}")
	public Task getTask(@PathVariable Long id) {
		return taskService.getTask(id);
	}
	
	@PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest request) {
		Task createdTask = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
	
	@PutMapping("/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest request){
		Task updatedTask = taskService.updateTask(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedTask);
	}
}

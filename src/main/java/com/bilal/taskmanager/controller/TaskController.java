package com.bilal.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping
	public List<Task> getAllTasks(){
		return taskService.getAllTasks();
	}
	
	@PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest request) {
		Task createdTask = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
}

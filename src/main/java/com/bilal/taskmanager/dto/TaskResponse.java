package com.bilal.taskmanager.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates Getters/Setters
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
	private Long id;
	private String title;
	private String description;
	private LocalDate createdAt;
	private boolean completed = false;
}

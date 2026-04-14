package com.bilal.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequest {

	@NotBlank(message= "Title is mendatory")
	private String title;
	
	private String description;
	
	private Boolean completed;
}

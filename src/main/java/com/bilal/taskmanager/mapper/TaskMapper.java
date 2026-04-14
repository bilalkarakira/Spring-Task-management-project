package com.bilal.taskmanager.mapper;

import org.mapstruct.Mapper;

import com.bilal.taskmanager.dto.TaskResponse;
import com.bilal.taskmanager.model.Task;


@Mapper(componentModel = "spring")
public interface TaskMapper {
	TaskResponse taskToTaskResponse(Task task);
}

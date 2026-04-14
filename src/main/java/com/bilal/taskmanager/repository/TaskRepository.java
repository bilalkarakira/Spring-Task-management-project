package com.bilal.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bilal.taskmanager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}

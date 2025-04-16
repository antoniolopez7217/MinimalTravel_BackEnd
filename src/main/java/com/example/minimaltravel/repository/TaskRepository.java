package com.example.minimaltravel.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minimaltravel.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {}

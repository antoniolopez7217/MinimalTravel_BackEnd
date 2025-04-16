package com.example.minimaltravel.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minimaltravel.model.User;

public interface UserRepository extends JpaRepository<User, Long> {}

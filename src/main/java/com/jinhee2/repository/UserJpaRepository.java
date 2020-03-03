package com.jinhee2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jinhee2.model.User;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
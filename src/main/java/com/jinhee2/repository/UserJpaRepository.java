package com.jinhee2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jinhee2.model.Users;

public interface UserJpaRepository extends JpaRepository<Users, Integer> {
	Users findByName(String name);
}
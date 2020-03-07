package com.jinhee2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jinhee2.model.Users;

public interface UserJpaRepository extends JpaRepository<Users, Integer> {
	Users findByName(String name);
	Users getOne(Integer id);
//	List<User> findAll(Integer id, String name);
}
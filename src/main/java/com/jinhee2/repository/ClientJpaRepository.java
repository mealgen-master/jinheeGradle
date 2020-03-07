package com.jinhee2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jinhee2.model.OauthClientDetails;

public interface ClientJpaRepository extends JpaRepository<OauthClientDetails, Integer> {
	
}

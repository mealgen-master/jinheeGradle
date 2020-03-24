package com.jinhee2.repository;

import com.jinhee2.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<UserRole, Integer> {
//    Optional<UserRole> findByRole(UserRole.rolename rolename);
}

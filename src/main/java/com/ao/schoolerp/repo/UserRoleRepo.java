package com.ao.schoolerp.repo;

import com.ao.schoolerp.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
    Optional<UserRole> findByName(String roleName);
}

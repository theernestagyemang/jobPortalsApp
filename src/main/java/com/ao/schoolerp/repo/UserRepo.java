package com.ao.schoolerp.repo;


import com.ao.schoolerp.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser,Integer> {
    Optional<AppUser> findByUsername(String username);

}
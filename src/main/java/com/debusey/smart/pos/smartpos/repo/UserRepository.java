/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.repo;

/**
 * @author Admin
 */

import com.debusey.smart.pos.smartpos.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String userName);

    Optional<Users> findByEmail(String email);

    Optional<Users> findByResetToken(String resetToken);

    Optional<Users> findByActivationToken(String resetToken);

    @Query("SELECT u FROM Users u WHERE  u.username = :username")
    List<Users> findByUserEmail(@Param("username") String username);

    Page<Users> findByUserType(String userType, Pageable of);
}

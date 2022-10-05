package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceTypeRepo extends JpaRepository<ServiceType, Integer> {

    Optional<ServiceType> findByName(String name);
}

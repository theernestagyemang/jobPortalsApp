package com.ao.schoolerp.repo;

import com.ao.schoolerp.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepo extends JpaRepository<Maintenance, Integer> {
}

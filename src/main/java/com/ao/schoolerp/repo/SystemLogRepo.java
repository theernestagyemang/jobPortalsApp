package com.ao.schoolerp.repo;

import com.ao.schoolerp.entities.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemLogRepo extends JpaRepository<SystemLog, Integer> {
}

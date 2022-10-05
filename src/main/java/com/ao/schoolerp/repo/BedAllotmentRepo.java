package com.ao.schoolerp.repo;

import com.ao.schoolerp.entities.BedAllotment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BedAllotmentRepo extends JpaRepository<BedAllotment, Integer> {
}

package com.ao.schoolerp.repo;

import com.ao.schoolerp.entities.HostelBlock;
import com.ao.schoolerp.entities.HostelFloor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HostelFloorRepo extends JpaRepository<HostelFloor, Integer> {
    List<HostelFloor> findByHostelBlock(HostelBlock hostelBlock);
}

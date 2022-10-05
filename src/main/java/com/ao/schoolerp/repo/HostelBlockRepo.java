package com.ao.schoolerp.repo;

import com.ao.schoolerp.entities.Hostel;
import com.ao.schoolerp.entities.HostelBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HostelBlockRepo extends JpaRepository<HostelBlock, Integer> {
    List<HostelBlock> findByHostel(Hostel hostel);
}

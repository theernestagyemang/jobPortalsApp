package com.ao.schoolerp.repo;

import com.ao.schoolerp.entities.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostelRepo extends JpaRepository<Hostel, Integer> {
}

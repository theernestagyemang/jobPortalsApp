package com.ao.schoolerp.repo;

import com.ao.schoolerp.entities.HostelFloor;
import com.ao.schoolerp.entities.HostelRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HostelRoomRepo extends JpaRepository<HostelRoom, Integer> {
    List<HostelRoom> findByHostelFloor(HostelFloor hostelFloor);
}

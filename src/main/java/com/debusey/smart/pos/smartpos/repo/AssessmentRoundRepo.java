package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.AssessmentRound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRoundRepo extends JpaRepository<AssessmentRound, Integer> {
    public AssessmentRound findByRoundPriority(Integer roundPriority);
}

package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.Assessment;
import com.debusey.smart.pos.smartpos.entity.AssessmentLines;
import com.debusey.smart.pos.smartpos.entity.AssessmentRound;
import com.debusey.smart.pos.smartpos.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentTransactionRepo extends JpaRepository<Assessment, Integer> {

    List<Assessment> findByUsersAndStatus(Users users, boolean status);

    Assessment findByUsersAndAssessmentLinesAndStatus(Users users, AssessmentLines assessmentLines, boolean status);

    Assessment findByUsersAndAssessmentLinesAndStatusAndAssessmentRound
            (Users users, AssessmentLines assessmentLines,
             boolean status, AssessmentRound assessmentRound);

    List<Assessment> findByUsersAndAssessmentLinesAndStatusAndUserType(
            Users users, AssessmentLines assessmentLines, boolean status,String userType);
}

package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.AssessmentLines;
import com.debusey.smart.pos.smartpos.entity.UserPassTest;
import com.debusey.smart.pos.smartpos.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPassTestRepo extends JpaRepository<UserPassTest, Integer> {
    List<UserPassTest> findByUsers(Users users);

    UserPassTest findByUsersAndAssessmentLines(Users users, AssessmentLines assessmentLines);
}

package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.AssessmentLines;
import com.debusey.smart.pos.smartpos.entity.AssessmentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentQuestionRepo extends JpaRepository<AssessmentQuestion, Integer> {

    List<AssessmentQuestion> findByAssessmentLines(AssessmentLines assessmentLines);
}

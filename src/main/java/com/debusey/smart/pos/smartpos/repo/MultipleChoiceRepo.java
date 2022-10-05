package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.AssessmentQuestion;
import com.debusey.smart.pos.smartpos.entity.MultipleChoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MultipleChoiceRepo extends JpaRepository<MultipleChoice, Integer> {
    List<MultipleChoice> findByAssessmentQuestion(AssessmentQuestion assessmentQuestion);

    List<MultipleChoice> findByAssessmentLine(String assessment);
}

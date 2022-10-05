package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.AssessmentLines;
import com.debusey.smart.pos.smartpos.entity.AssessmentQuestion;
import com.debusey.smart.pos.smartpos.repo.AssessmentQuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssessmentQuestionService {
    @Autowired
    public AssessmentQuestionRepo assessmentQuestionRepo;

    public List<AssessmentQuestion> findAll() {
        List<AssessmentQuestion> items = new ArrayList<>();
        assessmentQuestionRepo.findAll().forEach(items::add);

        return items;
    }

    public AssessmentQuestion save(AssessmentQuestion assessmentQuestion) {
        try {
            return assessmentQuestionRepo.save(assessmentQuestion);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean saveAll(List<AssessmentQuestion> assessmentQuestionList) {
        try {
            assessmentQuestionRepo.saveAll(assessmentQuestionList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void delete(AssessmentQuestion assessmentQuestion) {
        assessmentQuestionRepo.delete(assessmentQuestion);
    }

    public AssessmentQuestion findById(Integer id) {
        return assessmentQuestionRepo.findById(id).orElse(null);
    }

    public List<AssessmentQuestion> findQuestionsByAssessmentLines(AssessmentLines assessmentLines) {
        try {
            return assessmentQuestionRepo.findByAssessmentLines(assessmentLines);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id) {
        try {
            assessmentQuestionRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

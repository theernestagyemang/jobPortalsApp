package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.AssessmentQuestion;
import com.debusey.smart.pos.smartpos.entity.MultipleChoice;
import com.debusey.smart.pos.smartpos.repo.MultipleChoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MultipleChoiceService {
    @Autowired
    private MultipleChoiceRepo multipleChoiceRepo;

    public List<MultipleChoice> findAll() {
        try {
            return multipleChoiceRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MultipleChoice findById(Integer id) {
        try {
            return multipleChoiceRepo.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MultipleChoice> findAllByAssessmentQuestion(AssessmentQuestion assessmentQuestion) {
        try {
            return multipleChoiceRepo.findByAssessmentQuestion(assessmentQuestion);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MultipleChoice> findAllByAssessment(String assessment) {
        try {
            return multipleChoiceRepo.findByAssessmentLine(assessment);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean saveAll(List<MultipleChoice> multipleChoiceList) {
        try {
            multipleChoiceRepo.saveAll(multipleChoiceList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveOne(MultipleChoice multipleChoice) {
        try {
            multipleChoiceRepo.save(multipleChoice);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(Integer id) {
        try {
            multipleChoiceRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

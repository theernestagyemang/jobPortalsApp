package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.CourseQuiz;
import com.debusey.smart.pos.smartpos.entity.QuizOption;
import com.debusey.smart.pos.smartpos.repo.QuizOptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizOptionService {

    @Autowired
    private QuizOptionRepo quizOptionRepo;


    public List<QuizOption> findAll() {
        try {
            return quizOptionRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<QuizOption> findAllByQuestion(CourseQuiz courseQuiz) {
        try {
            return quizOptionRepo.findByCourseQuiz(courseQuiz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<QuizOption> findAllByCourse(String course) {
        try {
            return quizOptionRepo.findByCourse(course);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public QuizOption findById(Integer id) {
        try {
            return quizOptionRepo.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteById(Integer id) {
        try {
            quizOptionRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAll(List<QuizOption> quizOptions) {
        try {
            quizOptionRepo.saveAll(quizOptions);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean save(QuizOption quizOption) {
        try {
            quizOptionRepo.save(quizOption);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

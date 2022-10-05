package com.debusey.smart.pos.smartpos.repo;

import com.debusey.smart.pos.smartpos.entity.CourseQuiz;
import com.debusey.smart.pos.smartpos.entity.QuizOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizOptionRepo extends JpaRepository<QuizOption, Integer> {
    List<QuizOption> findByCourseQuiz(CourseQuiz courseQuiz);
    List<QuizOption> findByCourse(String course);
}

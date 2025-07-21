package com.raji.rajiQuizApp.Dao;

import com.raji.rajiQuizApp.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {

    List<Question> findByCategory(String category);

    @Query(value ="SELECT * FROM question1 q where q.category=?1 ORDER BY RANDOM() LIMIT ?2",nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, Integer numQ);
}

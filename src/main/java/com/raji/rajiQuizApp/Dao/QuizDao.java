package com.raji.rajiQuizApp.Dao;

import com.raji.rajiQuizApp.Model.Quiz1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz1,Integer> {
}

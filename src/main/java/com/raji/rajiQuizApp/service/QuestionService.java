package com.raji.rajiQuizApp.service;

import com.raji.rajiQuizApp.Dao.QuestionDao;
import com.raji.rajiQuizApp.Model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        return questionDao.findAll();

    }

    public String addQuestion(Question question) {
        questionDao.save(question);
        return "success";

    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }
}

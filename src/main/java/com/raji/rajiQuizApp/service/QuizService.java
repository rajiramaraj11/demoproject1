package com.raji.rajiQuizApp.service;

import com.raji.rajiQuizApp.Dao.QuestionDao;
import com.raji.rajiQuizApp.Dao.QuizDao;
import com.raji.rajiQuizApp.Model.Question;
import com.raji.rajiQuizApp.Model.QuestionWrapper;
import com.raji.rajiQuizApp.Model.Quiz1;
import com.raji.rajiQuizApp.Model.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    private static final Logger log = LoggerFactory.getLogger(QuizService.class);

    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {
        Quiz1 quiz1=new Quiz1();
        quiz1.setTitle(title);
        List<Question> questions=questionDao.findRandomQuestionsByCategory(category,numQ);
        //if (questions == null || questions.isEmpty()) {
          //  return  new ResponseEntity<>("NO questions found for category: "+category,HttpStatus.BAD_REQUEST);

        //}
        quiz1.setQuestions(questions);
        quizDao.save(quiz1);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQestions(Integer id) {
       Optional<Quiz1> quiz1 =quizDao.findById(id);
       //if quiz id is not there,it throws ann eror
        log.info("Received request for quiz ID: {}", id);

        if (!quiz1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Question> questionsFromDB= quiz1.get().getQuestions();
        log.info("Questions retrieved: {}", questionsFromDB.size());
        List<QuestionWrapper> questionsForUser=new ArrayList<>();
        for(Question q:questionsFromDB) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz1 quiz1=quizDao.findById(id).get();
        List<Question> questions= quiz1.getQuestions();
        int right=0;
        int i=0;
        for(Response response:responses)
        {
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;
            i++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}

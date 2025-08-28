package com.example.healthplace.SpringServer.controller;

import com.example.healthplace.SpringServer.model.AnswerModel.Answer;
import com.example.healthplace.SpringServer.model.AnswerModel.AnswerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnswerController {
    @Autowired
    private AnswerDao answerDao;

    @PostMapping("/saveAnswer")
    public void save(@RequestBody Answer answer){
        answerDao.saveAnswer(answer);
    }

    @GetMapping("/getAllAnswer")
    public List<Answer> getAnswers(){
        return answerDao.getAllAnswer();
    }

    @GetMapping("/getAnswerForQuestion/{idQuestion}")
    public List<Answer> getAnswerForQuestion(@PathVariable int idQuestion){
        return answerDao.getAnswerForQuestion(idQuestion);
    }
}

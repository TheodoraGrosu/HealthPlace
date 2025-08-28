package com.example.healthplace.SpringServer.controller;

import com.example.healthplace.SpringServer.model.QuestionModel.Question;
import com.example.healthplace.SpringServer.model.QuestionModel.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class QuestionController {
    @Autowired
    QuestionDao questionDao;

    @PostMapping("/saveQuestion")
    public void save(@RequestBody Question question){
        questionDao.saveQuestion(question);
    }

    @GetMapping("/getQuestions")
    public List<Question> getQuestion(){
        return questionDao.getAllQuestion();
    }

    @PostMapping("/updateStateQuestion/{idQuestion}")
    public void updateStateQuestion(@PathVariable int idQuestion){
        questionDao.updateStateQuestion(idQuestion);
    }

    @GetMapping("/getMyQuestions/{idPatient}")
    public List<Question> getMyQuestion(@PathVariable int idPatient){
        return questionDao.getMyQuestion(idPatient);
    }
}

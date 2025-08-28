package com.example.healthplace.SpringServer.model.AnswerModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerDao {
    @Autowired
    AnswerRepository answerRepository;

    public void saveAnswer(Answer answer){
        Answer answer1 = Answer.builder()
                .idQuestion(answer.getIdQuestion())
                .idDoctor(answer.getIdDoctor())
                .answerText(answer.getAnswerText())
                .build();
        answerRepository.save(answer1);
    }

    public List<Answer> getAllAnswer(){
        List<Answer> answerList = new ArrayList<>();
        Streamable.of(answerRepository.findAll()).forEach(answer -> {
            answerList.add(answer);
        });
        return  answerList;
    }

    public List<Answer> getAnswerForQuestion(int idQuestion){
        List<Answer> answerList = new ArrayList<>();
        Streamable.of(answerRepository.findAll()).forEach(answer -> {
            if(answer.getIdQuestion() == idQuestion) {
                answerList.add(answer);
            }
        });
        return  answerList;
    }
}

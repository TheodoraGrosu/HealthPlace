package com.example.healthplace.SpringServer.model.QuestionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionDao {
    @Autowired
    QuestionRepository questionRepository;

    public void saveQuestion(Question questionRequest){
        Question question = Question.builder()
                .idPatient(questionRequest.getIdPatient())
                .patientName(questionRequest.getPatientName())
                .questionText(questionRequest.getQuestionText())
                .is_confirmed(false)
                .build();
        questionRepository.save(question);
    }

    public List<Question> getAllQuestion(){
        List<Question> questionsList = new ArrayList<>();
        questionRepository.findAll().forEach(question -> {
            questionsList.add(question);
        });
        return questionsList;

    }

    public void updateStateQuestion(int idQuestion){
        Question question = questionRepository.findById(idQuestion).get();
        question.setIs_confirmed(true);
        questionRepository.save(question);
    }

    public List<Question> getMyQuestion(int idPatient){
        List<Question> questionsList = new ArrayList<>();
        questionRepository.findAll().forEach(question -> {
            if(question.getIdPatient() == idPatient){
                questionsList.add(question);
            }
        });
        return questionsList;
    }
}

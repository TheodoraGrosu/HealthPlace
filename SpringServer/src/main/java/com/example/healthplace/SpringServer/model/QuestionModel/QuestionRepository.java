package com.example.healthplace.SpringServer.model.QuestionModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findByIdQuestion(int idQuestion);
}

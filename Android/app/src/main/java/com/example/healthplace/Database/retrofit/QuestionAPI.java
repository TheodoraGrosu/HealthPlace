package com.example.healthplace.Database.retrofit;

import com.example.healthplace.Database.model.Question;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QuestionAPI {
    @POST("/saveQuestion")
    Call<Void> save(@Body Question question);

    @GET("/getQuestions")
    Call<List<Question>> getAllQuestions();

    @POST("/updateStateQuestion/{idQuestion}")
    Call<Void> updateState(@Path("idQuestion") int idQuestion);

    @GET("/getMyQuestions/{idPatient}")
    Call<List<Question>> getMyQuestions(@Path("idPatient") int idPatient);
}

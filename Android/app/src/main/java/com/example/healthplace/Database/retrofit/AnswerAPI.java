package com.example.healthplace.Database.retrofit;

import com.example.healthplace.Database.model.Answer;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AnswerAPI {

    @POST("/saveAnswer")
    Call<Void> save(@Body Answer answer);

    @GET("/getAllAnswer")
    Call<List<Answer>> getAllAnswer();

    @GET("/getAnswerForQuestion/{idQuestion}")
    Call<List<Answer>> getAnswerForQuestion(@Path("idQuestion") int idQuestion);
}

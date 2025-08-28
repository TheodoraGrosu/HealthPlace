package com.example.healthplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.example.healthplace.Database.model.Doctor;
import com.example.healthplace.Database.model.Question;
import com.example.healthplace.Database.retrofit.QuestionAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorAnswer extends AppCompatActivity {
    RecyclerView recyclerView;
    RetrofitService retrofitService = new RetrofitService();
    QuestionAPI questionAPI = retrofitService.getRetrofit().create(QuestionAPI.class);
    Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_answer);
        recyclerView = findViewById(R.id.recyclerViewDoctorAnswer);
        Intent intent = getIntent();
        doctor = (Doctor) intent.getSerializableExtra("doctor");
        getAllQuestion();
    }

    public void getAllQuestion(){
        Call<List<Question>> questionList = questionAPI.getAllQuestions();
        questionList.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if(response.isSuccessful()){
                    List<Question> list = response.body();
                    RecyclerAdapterAnswer recyclerAdapter = new RecyclerAdapterAnswer(DoctorAnswer.this, list, doctor.getId());
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(DoctorAnswer.this, LinearLayoutManager.VERTICAL, false));
                }
                else{
                    Log.d("Error", "onResponse: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}
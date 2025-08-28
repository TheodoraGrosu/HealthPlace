package com.example.healthplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.example.healthplace.Database.model.Question;
import com.example.healthplace.Database.retrofit.QuestionAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllQuestions extends AppCompatActivity {

    RecyclerView recyclerView;
    Switch myQuestionSwitch;
    RetrofitService retrofitService = new RetrofitService();
    QuestionAPI questionAPI = retrofitService.getRetrofit().create(QuestionAPI.class);

    public void initializeComponents(View view){
        recyclerView = view.findViewById(R.id.recyclerViewAnswer);
        myQuestionSwitch = view.findViewById(R.id.myQuestion);
        myQuestionSwitch.setChecked(false);
    }


    public void getAllAnswer(Context context){

            Call<List<Question>> questionList = questionAPI.getAllQuestions();
            questionList.enqueue(new Callback<List<Question>>() {
                @Override
                public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                    if (response.isSuccessful()) {
                        List<Question> list = response.body();
                        RecyclerAdapterQuestion recyclerAdapter = new RecyclerAdapterQuestion(context, list);
                        recyclerView.setAdapter(recyclerAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(AllQuestions.this, LinearLayoutManager.VERTICAL, false));
                    } else {
                        Log.d("Error", "onResponse: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<Question>> call, Throwable t) {
                    Log.d("Error", "onFailure: " + t.getMessage());
                }
            });
        }


    public void getMyQuestion(Context context, int idPatient){
        myQuestionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Call<List<Question>> questionList = questionAPI.getMyQuestions(idPatient);
                    questionList.enqueue(new Callback<List<Question>>() {
                        @Override
                        public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                            if(response.isSuccessful()){
                                List<Question> list = response.body();
                                RecyclerAdapterQuestion recyclerAdapter = new RecyclerAdapterQuestion(context, list);
                                recyclerView.setAdapter(recyclerAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(AllQuestions.this, LinearLayoutManager.VERTICAL, false));
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
                else{
                    getAllAnswer(context);
                }
            }
        });

    }
}
package com.example.healthplace;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthplace.Database.model.Answer;
import com.example.healthplace.Database.model.Doctor;
import com.example.healthplace.Database.model.Question;
import com.example.healthplace.Database.retrofit.AnswerAPI;
import com.example.healthplace.Database.retrofit.DoctorAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerItemQuestion extends RecyclerView.ViewHolder{
    TextView questionTextTV;
    TextView patientNameTV;
    LinearLayout linearLayout;
    RetrofitService retrofitService = new RetrofitService();
    AnswerAPI answerAPI = retrofitService.getRetrofit().create(AnswerAPI.class);
    DoctorAPI doctorAPI = retrofitService.getRetrofit().create(DoctorAPI.class);
    static Map<Integer, String> doctorMapName = new HashMap<>();


    public RecyclerItemQuestion( View itemView) {
        super(itemView);
        questionTextTV = itemView.findViewById(R.id.tvQuestionText);
        patientNameTV = itemView.findViewById(R.id.patientName);
        linearLayout = itemView.findViewById(R.id.linearLayoutAnswers);
        getDoctorName();
    }

    public void bindData(Question question, Context context) {

       questionTextTV.setText(question.getQuestionText());
       patientNameTV.setText(question.getPatientName());
        Thread databaseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Call<List<Answer>> answerList = answerAPI.getAnswerForQuestion(question.getIdQuestion());
                answerList.enqueue(new Callback<List<Answer>>() {
                    @Override
                    public void onResponse(Call<List<Answer>> call, Response<List<Answer>> response) {
                        if (response.isSuccessful()) {
                            List<Answer> list = response.body();
                            for (Answer answer : list) {
                                TextView textView = new TextView(context);
                                textView.setTextColor(context.getResources().getColor(R.color.albastr));
                                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                                String doctorName = doctorMapName.get(answer.getIdDoctor());
                                textView.setText(doctorName + " : " + answer.getAnswerText());
                                linearLayout.addView(textView);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Answer>> call, Throwable t) {

                    }
                });
            }});
        databaseThread.start();

    }

    public void getDoctorName(){
        Call<List<Doctor>> doctorList = doctorAPI.getAllDoctors();
        doctorList.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                if(response.isSuccessful()){
                    List<Doctor> list = response.body();
                    for(Doctor doctor : list){
                        doctorMapName.put(doctor.getId(), "Dr. " + doctor.getLastName() + " " + doctor.getFirstName());
                    }
                }
                else{
                    Log.d("Response", "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}

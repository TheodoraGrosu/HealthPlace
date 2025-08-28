package com.example.healthplace;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthplace.Database.model.Patient;
import com.example.healthplace.Database.model.Question;
import com.example.healthplace.Database.retrofit.PatientAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerItemAnswer extends RecyclerView.ViewHolder{
    TextView questionText;
    TextView patientNameTV;
    EditText answerTextET;
    Button saveAnswer;
    CardView cardView;
    RetrofitService retrofitService = new RetrofitService();
    PatientAPI patientAPI = retrofitService.getRetrofit().create(PatientAPI.class);
    static Map<Integer, String> patientNameMap = new HashMap<>();

    public RecyclerItemAnswer(@NonNull View itemView) {
        super(itemView);
        questionText = itemView.findViewById(R.id.tvQuestionText);
        patientNameTV = itemView.findViewById(R.id.patientName);
        answerTextET = itemView.findViewById(R.id.doctorAnswerET);
        saveAnswer = itemView.findViewById(R.id.save);
        cardView = itemView.findViewById(R.id.cardView);
    }

    public void bindData(Question question, Context context){
        getPatientName();
        questionText.setText(question.getQuestionText());
        patientNameTV.setText(question.getPatientName());
    }

    public void getPatientName(){
        Call<List<Patient>> patientList = patientAPI.getAllPatients();
        patientList.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if(response.isSuccessful()){
                    List<Patient> list = response.body();
                    for(Patient patient : list){
                        patientNameMap.put(patient.getId(), patient.getLastName() + " " + patient.getFirstName());
                    }
                }
                else{
                    Log.d("Response", "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}

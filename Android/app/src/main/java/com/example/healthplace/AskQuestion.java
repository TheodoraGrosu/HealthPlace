package com.example.healthplace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.healthplace.Database.model.Question;
import com.example.healthplace.Database.retrofit.QuestionAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskQuestion {
    EditText questionTextET;
    Button butonSave;
    TextView allQuestionsTV;
    BottomSheetDialog dialog ;
    RetrofitService retrofitService = new RetrofitService();
    QuestionAPI questionAPI = retrofitService.getRetrofit().create(QuestionAPI.class);
    public static int idPatient;


    public void initializeComponents(View view)
    {
        questionTextET = view.findViewById(R.id.questionText);
        butonSave = view.findViewById(R.id.buttonSaveQuestion);
        allQuestionsTV = view.findViewById(R.id.allQuestions);
    }


    public void saveQuestion(Context context, int patientId, String patientName){
        idPatient = patientId;
        butonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question question = new Question();
                question.setIdPatient(patientId);
                question.setPatientName(patientName);
                question.setQuestionText(questionTextET.getText().toString());
                Call<Void> callQuestion = questionAPI.save(question);
                callQuestion.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful())
                        {
                            questionTextET.setText("");
                            Toast.makeText(context, "Thank you! Our doctors will answer as soon as possible!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, "Sorry! Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, "Sorry! Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void getAllQuestions(Context context){
        allQuestionsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(context).inflate(R.layout.activity_all_questions, null);
                dialog = new BottomSheetDialog(context);
                dialog.setContentView(view);
                View bottomSheet = dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet);
                bottomSheet.getLayoutParams().height = WindowManager.LayoutParams.MATCH_PARENT;
                AllQuestions allQuestions = new AllQuestions();
                allQuestions.initializeComponents(view);
                allQuestions.getAllAnswer(context);
                allQuestions.getMyQuestion(context, idPatient);

                dialog.show();
            }
        });
    }
}













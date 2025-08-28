package com.example.healthplace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthplace.Database.model.Answer;
import com.example.healthplace.Database.model.Question;
import com.example.healthplace.Database.retrofit.AnswerAPI;
import com.example.healthplace.Database.retrofit.QuestionAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerAdapterAnswer extends RecyclerView.Adapter<RecyclerItemAnswer>{
    private Context context;
    private List<Question> questionList;
    private int idDoctor;
    RetrofitService retrofitService = new RetrofitService();
    AnswerAPI answerAPI = retrofitService.getRetrofit().create(AnswerAPI.class);
    QuestionAPI questionAPI = retrofitService.getRetrofit().create(QuestionAPI.class);

    public RecyclerAdapterAnswer(Context context, List<Question> questionList, int idDoctor) {
        this.questionList = questionList;
        this.context = context;
        this.idDoctor = idDoctor;
    }

    @NonNull
    @Override
    public RecyclerItemAnswer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.answer, parent, false);
        RecyclerItemAnswer recyclerItem = new RecyclerItemAnswer(itemView);
        return recyclerItem;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItemAnswer holder,@SuppressLint("RecyclerView") int position) {
        Question question = questionList.get(position);
        holder.bindData(question, context);
        holder.saveAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer answer = new Answer();
                if(holder.answerTextET.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please enter your answer!", Toast.LENGTH_SHORT).show();

                }
                else {
                    answer.setAnswerText(holder.answerTextET.getText().toString());

                    answer.setIdQuestion(question.getIdQuestion());
                    answer.setIdDoctor(idDoctor);
                    Call<Void> answerCall = answerAPI.save(answer);
                    answerCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Call<Void> questionCall = questionAPI.updateState(question.getIdQuestion());
                                questionCall.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        questionList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, questionList.size());
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}


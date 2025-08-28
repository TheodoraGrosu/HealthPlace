package com.example.healthplace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthplace.Database.model.Question;
import java.util.List;

public class RecyclerAdapterQuestion extends RecyclerView.Adapter<RecyclerItemQuestion>{
    private Context context;
    private List<Question> questionList;

    public RecyclerAdapterQuestion(Context context, List<Question> questionList) {
        this.questionList = questionList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerItemQuestion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.question, parent, false);
        RecyclerItemQuestion recyclerItem = new RecyclerItemQuestion(itemView);
        return recyclerItem;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItemQuestion holder, int position) {
        Question question = questionList.get(position);
        holder.bindData(question, context);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}

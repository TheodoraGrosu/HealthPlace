package com.example.healthplace;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthplace.Database.model.OnlineAppointment;
import com.example.healthplace.Database.retrofit.PatientAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerItemManagement extends RecyclerView.ViewHolder{
    TextView service;
    TextView date;
    TextView time;
    TextView patient;
    ImageView imageView;
    CardView cardView;
    ImageButton yes;
    ImageButton no;
    RetrofitService retrofitService = new RetrofitService();
    PatientAPI patientAPI = retrofitService.getRetrofit().create(PatientAPI.class);

    public RecyclerItemManagement(View itemView) {
        super(itemView);
        service = itemView.findViewById(R.id.tvService);
        date = itemView.findViewById(R.id.tvDate);
        time = itemView.findViewById(R.id.tvTime);
        patient = itemView.findViewById(R.id.tvPatientName);
        imageView = itemView.findViewById(R.id.image);
        cardView = itemView.findViewById(R.id.cardView);
        yes = itemView.findViewById(R.id.imageButtonYes);
        no = itemView.findViewById(R.id.imageButtonNo);
    }

    public void bindData(OnlineAppointment onlineAppointment) {
        service.setText(onlineAppointment.getService());

                Call<List<String>> call = patientAPI.getPatientName(onlineAppointment.getIdPatient());
                call.enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if (response.isSuccessful()) {
                            List<String> name = response.body();
                            patient.setText(name.get(0) + " " + name.get(1));
                            Log.d("Response", "onResponse: " + name.get(0) + " " + name.get(1));
                        } else {
                            Log.d("Error", "onResponse: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        Log.d("Error", "onFailure: " + t.getMessage());
                    }
                });
                date.setText(onlineAppointment.getDate());
                time.setText(onlineAppointment.getTime());
            }
    }




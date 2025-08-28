package com.example.healthplace;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthplace.Database.model.OnlineAppointment;
import com.example.healthplace.Database.retrofit.DoctorAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerItem extends RecyclerView.ViewHolder {
    TextView department;
    TextView service;
    TextView doctor;
    TextView date;
    TextView time;
    ImageView imageView;
    Button cancel;
    CardView cardView;
    RetrofitService retrofitService = new RetrofitService();
    DoctorAPI doctorAPI = retrofitService.getRetrofit().create(DoctorAPI.class);

    public RecyclerItem( View itemView) {
        super(itemView);
        department = itemView.findViewById(R.id.tvDepartment);
        service = itemView.findViewById(R.id.tvService);
        doctor = itemView.findViewById(R.id.tvDoctor);
        date = itemView.findViewById(R.id.tvDate);
        time = itemView.findViewById(R.id.tvTime);
        imageView = itemView.findViewById(R.id.image);
        cardView = itemView.findViewById(R.id.cardView);
        cancel = itemView.findViewById(R.id.buttonCancel);
    }


    public void bindData(OnlineAppointment onlineAppointment) {
        department.setText(onlineAppointment.getDepartment());
        service.setText(onlineAppointment.getService());
        Thread databaseThread = new Thread(new Runnable() {
            @Override
            public void run() {
              Call<List<String>> call = doctorAPI.getDoctorName(onlineAppointment.getIdDoctor());
              call.enqueue(new Callback<List<String>>() {
                  @Override
                  public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                      if(response.isSuccessful()){
                            List<String> name = response.body();
                            doctor.setText(name.get(0) + " " + name.get(1));
                            Log.d("Response", "onResponse: " + name.get(0) + " " + name.get(1));
                      }
                  }

                  @Override
                  public void onFailure(Call<List<String>> call, Throwable t) {

                  }
              });
            }
        });
        databaseThread.start();
        date.setText(onlineAppointment.getDate());
        time.setText(onlineAppointment.getTime());
    }

}





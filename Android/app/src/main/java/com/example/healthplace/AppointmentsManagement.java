package com.example.healthplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.example.healthplace.Database.model.Doctor;
import com.example.healthplace.Database.model.OnlineAppointment;
import com.example.healthplace.Database.retrofit.OnlineAppointmentAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentsManagement extends AppCompatActivity {
    RecyclerView recyclerView;
    Doctor doctor;
    RetrofitService retrofitService = new RetrofitService();
    OnlineAppointmentAPI onlineAppointmentAPI = retrofitService.getRetrofit().create(OnlineAppointmentAPI.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_management);
        recyclerView = findViewById(R.id.recyclerAppointmentsM);
        Intent intent = getIntent();
        doctor = (Doctor)intent.getSerializableExtra("doctor");
        getAllApointments();

    }

    public void getAllApointments(){
        Call<List<OnlineAppointment>> appointments = onlineAppointmentAPI.getAppointmentsForDoctorRecycler(doctor.getId());
        appointments.enqueue(new Callback<List<OnlineAppointment>>() {
            @Override
            public void onResponse(Call<List<OnlineAppointment>> call, Response<List<OnlineAppointment>> response) {
                if(response.isSuccessful()){
                    List<OnlineAppointment> onlineAppointments = response.body();
                    RecyclerAdapterManagement recyclerAdapter = new RecyclerAdapterManagement(AppointmentsManagement.this, onlineAppointments);
                    recyclerView.setAdapter(recyclerAdapter);

                    recyclerView.setLayoutManager(new LinearLayoutManager(AppointmentsManagement.this, LinearLayoutManager.VERTICAL, false));
                }
                else{
                    Log.d("Error", "onResponse: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<List<OnlineAppointment>> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}
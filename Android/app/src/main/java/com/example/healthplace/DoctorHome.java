package com.example.healthplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.healthplace.Database.model.Doctor;
import com.example.healthplace.Database.model.OnlineAppointment;
import com.example.healthplace.Database.retrofit.OnlineAppointmentAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorHome extends AppCompatActivity {
    TextView tvNameDoctor;
    RecyclerView recyclerViewDoctor;
    ImageButton personalInfoButton;
    ImageButton appointmentButton;
    ImageButton questionButton;
    ImageButton logout;
    RetrofitService retrofitService = new RetrofitService();
    OnlineAppointmentAPI onlineAppointmentAPI = retrofitService.getRetrofit().create(OnlineAppointmentAPI.class);
    Doctor doctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        initializeComponents();

        Intent intent = getIntent();
        doctor = (Doctor)intent.getSerializableExtra("doctor");
        tvNameDoctor.setText(doctor.getFirstName());
        getAllApointments();

        personalInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DoctorHome.this, PersonalInfoDoctor.class);
                intent1.putExtra("info", doctor);
                startActivity(intent1);
            }
        });

        appointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DoctorHome.this, AppointmentsManagement.class);
                intent1.putExtra("doctor", doctor);
                startActivity(intent1);
            }
        });

        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DoctorHome.this, DoctorAnswer.class);
                intent1.putExtra("doctor", doctor);
                startActivity(intent1);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DoctorHome.this, Intermediate.class);
                startActivity(intent1);
            }
        });
    }


    public void initializeComponents(){
        tvNameDoctor = findViewById(R.id.tvName);
        recyclerViewDoctor = findViewById(R.id.recyclerView);
        personalInfoButton = findViewById(R.id.personalInfo);
        appointmentButton = findViewById(R.id.appointments);
        questionButton = findViewById(R.id.question);
        logout = findViewById(R.id.logoutButton);
    }

    public void getAllApointments(){
        Call<List<OnlineAppointment>> appointments = onlineAppointmentAPI.getAppointmentsForDoctor(doctor.getId());
        appointments.enqueue(new Callback<List<OnlineAppointment>>() {
            @Override
            public void onResponse(Call<List<OnlineAppointment>> call, Response<List<OnlineAppointment>> response) {
                if(response.isSuccessful()){
                    List<OnlineAppointment> onlineAppointments = response.body();
                    RecyclerAdapterDoctor recyclerAdapter = new RecyclerAdapterDoctor(DoctorHome.this, onlineAppointments);
                    recyclerViewDoctor.setAdapter(recyclerAdapter);

                    recyclerViewDoctor.setLayoutManager(new LinearLayoutManager(DoctorHome.this, LinearLayoutManager.VERTICAL, false));
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

    @Override
    protected void onResume() {
        super.onResume();
       getAllApointments();
    }
}
package com.example.healthplace;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.util.Log;
import android.view.View;
import com.example.healthplace.Database.model.OnlineAppointment;
import com.example.healthplace.Database.retrofit.OnlineAppointmentAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentDetails {
    RecyclerView recyclerView;
    RetrofitService retrofitService = new RetrofitService();
    OnlineAppointmentAPI onlineAppointmentAPI = retrofitService.getRetrofit().create(OnlineAppointmentAPI.class);

    public void initializeComponents(View view){
        recyclerView = view.findViewById(R.id.recyclerViewAppointments);
    }

    public void getAllApointments(Context context, int id){
        Call<List<OnlineAppointment>> appointments = onlineAppointmentAPI.getAppointmentsForPatient(id);
        appointments.enqueue(new Callback<List<OnlineAppointment>>() {
            @Override
            public void onResponse(Call<List<OnlineAppointment>> call, Response<List<OnlineAppointment>> response) {
                if(response.isSuccessful()){
                    List<OnlineAppointment> onlineAppointments = response.body();
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(context, onlineAppointments);
                    recyclerView.setAdapter(recyclerAdapter);

                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
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
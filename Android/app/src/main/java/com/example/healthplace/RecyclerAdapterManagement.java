package com.example.healthplace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthplace.Database.model.OnlineAppointment;
import com.example.healthplace.Database.retrofit.OnlineAppointmentAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerAdapterManagement extends RecyclerView.Adapter<RecyclerItemManagement>{
    private Context context;
    private List<OnlineAppointment> filteredList = new ArrayList<>();
    RetrofitService retrofitService = new RetrofitService();
    OnlineAppointmentAPI onlineAppointmentAPI = retrofitService.getRetrofit().create(OnlineAppointmentAPI.class);


    public RecyclerAdapterManagement(Context context, List<OnlineAppointment> filteredAppointmentList) {
        this.filteredList = filterFutureAppointments(filteredAppointmentList);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerItemManagement onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.item_appointments_management, parent, false);
        RecyclerItemManagement recyclerItem = new RecyclerItemManagement(itemView);
        return recyclerItem;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItemManagement holder,  @SuppressLint("RecyclerView")  int position) {
        OnlineAppointment onlineAppointment = filteredList.get(position);
        holder.bindData(onlineAppointment);
        holder.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlineAppointment.setIs_confirmed(true);
                Call<Void> call = onlineAppointmentAPI.appointmentConfirm(onlineAppointment.getIdAppointment());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            filteredList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, filteredList.size());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            }
        });
        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = onlineAppointment.getIdAppointment();
                Call<Void> call = onlineAppointmentAPI.deleteAppointment(id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Log.d("Response", "Appointment deleted successfully");
                            filteredList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, filteredList.size());
                            Log.d("Response", String.valueOf(filteredList.size()));
                            Toast.makeText(context, "Appointment deleted successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("Response", "Appointment cannot be deleted");
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public List<OnlineAppointment> filterFutureAppointments(List<OnlineAppointment> appointments) {
        List<OnlineAppointment> futureAppointments = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        for (OnlineAppointment onlineAppointment : appointments) {
            String appointmentDate = onlineAppointment.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date appointment = dateFormat.parse(appointmentDate);
                if (appointmentDate != null && currentDate != null) {
                    if (appointment.after(currentDate)  && onlineAppointment.getIs_confirmed() != true) {
                        futureAppointments.add(onlineAppointment);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return futureAppointments;
    }
}

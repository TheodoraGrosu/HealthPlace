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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerItem>{
    private List<OnlineAppointment> appointmentList;
    private Context context;
    RetrofitService retrofitService = new RetrofitService();
    OnlineAppointmentAPI onlineAppointmentAPI = retrofitService.getRetrofit().create(OnlineAppointmentAPI.class);

    public RecyclerAdapter(Context context, List<OnlineAppointment> appointmentList) {
        this.appointmentList = appointmentList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        RecyclerItem recyclerItem = new RecyclerItem(itemView);
        return recyclerItem;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItem holder, @SuppressLint("RecyclerView")  int position) {
        OnlineAppointment onlineAppointment = appointmentList.get(position);
        holder.bindData(onlineAppointment);
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        String appointmentDate = onlineAppointment.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date appointment = dateFormat.parse(appointmentDate);
            if (appointmentDate != null && currentDate != null) {
                if (appointment.after(currentDate)) {
                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.cream2));
                    Log.d("Response", String.valueOf(appointmentList.size()));
                    //Cancel appointment
                       holder.cancel.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Calendar calendarTV = Calendar.getInstance();
                               calendarTV.setTime(appointment);
                               calendarTV.add(Calendar.DATE, -3);
                               Date threeDaysBeforeAppointment = calendarTV.getTime();
                               if(currentDate.before(threeDaysBeforeAppointment) || currentDate.equals(threeDaysBeforeAppointment)){
                                    int id = onlineAppointment.getIdAppointment();
                                    Call<Void> call = onlineAppointmentAPI.deleteAppointment(id);
                                    call.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if(response.isSuccessful()){
                                                Log.d("Response", "Appointment deleted successfully");
                                                appointmentList.remove(position);
                                                notifyItemRemoved(position);
                                                notifyItemRangeChanged(position, appointmentList.size());
                                                Log.d("Response", String.valueOf(appointmentList.size()));
                                                Toast.makeText(context, "Appointment deleted successfully!", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            Log.d("Response", "Appointment cannot be deleted");
                                        }
                                    });
                               }
                               else{
                                   Log.d("Response", "Appointment cannot be deleted. There must be 3 days until the appointment date");
                                   Toast.makeText(context, "Appointment cannot be deleted. There must be 3 days until the appointment date", Toast.LENGTH_LONG).show();
                               }
                           }
                       });
                }
                else{
                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.recyclerItemRed));
                    Log.d("Response", "Appointment is not after current date; it's not available");
                    holder.cancel.setVisibility(View.INVISIBLE);
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }
}




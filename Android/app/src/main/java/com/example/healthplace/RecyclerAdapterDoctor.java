package com.example.healthplace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthplace.Database.model.OnlineAppointment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecyclerAdapterDoctor extends RecyclerView.Adapter<RecyclerItemDoctor>{
    private Context context;
    private List<OnlineAppointment> filteredList = new ArrayList<>();

    public RecyclerAdapterDoctor(Context context, List<OnlineAppointment> filteredAppointmentList) {
        this.filteredList = filterFutureAppointments(filteredAppointmentList);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerItemDoctor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.recycler_doctor, parent, false);
        RecyclerItemDoctor recyclerItem = new RecyclerItemDoctor(itemView);
        return recyclerItem;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItemDoctor holder, int position) {
        OnlineAppointment onlineAppointment = filteredList.get(position);
        holder.bindData(onlineAppointment);
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
                    if (appointment.after(currentDate)) {
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



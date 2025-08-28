package com.example.healthplace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.healthplace.Database.model.Doctor;
import java.util.List;

public class DoctorAdapter extends ArrayAdapter<Doctor> {
    private Context context;

    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        super(context, 0, doctorList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createCardView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createCardView(position, convertView, parent);
    }

    private View createCardView(int position, View convertView, ViewGroup parent) {
        Doctor doctor = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.doctor_card, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvSpecialty = convertView.findViewById(R.id.tvSpecialty);
        TextView tvDepartment = convertView.findViewById(R.id.tvDepartment);
        TextView tvEmail = convertView.findViewById(R.id.tvEmail);

        tvName.setText(doctor.getLastName() + " " + doctor.getFirstName());
        tvSpecialty.setText(doctor.getSpeciality());
        tvDepartment.setText(doctor.getDepartment());
        tvEmail.setText(doctor.getEmail());

        return convertView;
    }
}

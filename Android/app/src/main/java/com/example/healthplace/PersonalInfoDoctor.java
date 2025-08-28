package com.example.healthplace;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.healthplace.Database.model.Doctor;

public class PersonalInfoDoctor extends AppCompatActivity {
    TextView firstName;
    TextView lastName;
    TextView email;
    TextView department;
    TextView employmentDate;
    TextView salaryTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_doctor);
        initializeComponents();
        populateTextView();
    }

    public void initializeComponents(){
        firstName = findViewById(R.id.tvFirstName);
        lastName = findViewById(R.id.tvLastName);
        email = findViewById(R.id.tvEmail);
        department = findViewById(R.id.tvDepartment);
        employmentDate = findViewById(R.id.tvEmploymentDate);
        salaryTV = findViewById(R.id.tvSalary);
    }

    public void populateTextView(){
        Intent intent = getIntent();
        Doctor doctor = (Doctor)intent.getSerializableExtra("info");
        firstName.setText(doctor.getFirstName());
        lastName.setText(doctor.getLastName());
        email.setText(doctor.getEmail());
        department.setText(doctor.getDepartment());
        employmentDate.setText(doctor.getEmploymentDate().toString());
        salaryTV.setText(String.valueOf(doctor.getSalary()));
    }
}
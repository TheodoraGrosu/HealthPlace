package com.example.healthplace;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.healthplace.Database.model.Patient;
import com.google.android.material.bottomsheet.BottomSheetDialog;
public class Home extends AppCompatActivity {
    ImageButton personal_info;
    ImageButton medical_history;
    ImageButton appointment_details;
    ImageButton make_appointment;
    ImageButton question;
    ImageButton consultation_price;
    ImageButton logout;
    BottomSheetDialog dialog;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeComponents();

        Intent intent = getIntent();
        Patient patient = (Patient) intent.getSerializableExtra("patient");
        name.setText(patient.getFirstName());
        String fullName = patient.getLastName() + " " + patient.getFirstName();

        //Create View for BottomSheetDialog
        personal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_personal_info, null);
                dialog.setContentView(view);

                View bottomSheet = dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet);
                bottomSheet.getLayoutParams().height = WindowManager.LayoutParams.MATCH_PARENT;

                ImageView imageView = view.findViewById(R.id.myImage);
                imageView.setImageResource(R.drawable.man);

                PersonalInfo personalInfo = new PersonalInfo();
                personalInfo.initializeComponents(view);
                personalInfo.setPatientInfo(patient);
                personalInfo.updatePatient(patient);
                personalInfo.setPatientInfo(PersonalInfo.patientUpdate);

                dialog.show();
            }
        });

        make_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_make_appointments, null);
                dialog.setContentView(view);

                View bottomSheet = dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet);
                bottomSheet.getLayoutParams().height = WindowManager.LayoutParams.MATCH_PARENT;

                ImageView imageView = view.findViewById(R.id.myImage);
                imageView.setImageResource(R.drawable.programare);

                MakeAppointments makeAppointments = new MakeAppointments();
                makeAppointments.initializeComponents(view);
                makeAppointments.populateSpinner(Home.this);
                makeAppointments.save(Home.this, patient.getId());
                dialog.show();
            }
        });

        appointment_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_appointment_details, null);
                dialog.setContentView(view);

                View bottomSheet = dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet);
                bottomSheet.getLayoutParams().height = WindowManager.LayoutParams.MATCH_PARENT;
                AppointmentDetails appointmentDetails = new AppointmentDetails();
                appointmentDetails.initializeComponents(view);
                appointmentDetails.getAllApointments(Home.this, patient.getId());
                dialog.show();
            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_ask_question, null);
                dialog.setContentView(view);

                View bottomSheet = dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet);
                bottomSheet.getLayoutParams().height = WindowManager.LayoutParams.MATCH_PARENT;
                AskQuestion askQuestion = new AskQuestion();
                askQuestion.initializeComponents(view);
                askQuestion.saveQuestion(Home.this, patient.getId(), fullName);
                askQuestion.getAllQuestions(Home.this);

                dialog.show();

            }});


        consultation_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_consultation_price, null);
                dialog.setContentView(view);
                View bottomSheet = dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet);
                bottomSheet.getLayoutParams().height = WindowManager.LayoutParams.MATCH_PARENT;

                ConsultationPrice consultationPrice = new ConsultationPrice();
                consultationPrice.initComponents(view);
                consultationPrice.populateSpinner(Home.this);
                consultationPrice.showPrice(Home.this);
                dialog.show();

            }
        });

        medical_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_medical_history, null);
                dialog.setContentView(view);
                View bottomSheet = dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet);
                bottomSheet.getLayoutParams().height = WindowManager.LayoutParams.MATCH_PARENT;

                MedicalHistoryDetails medicalHistory = new MedicalHistoryDetails();
                medicalHistory.initializeComponents(view);
                medicalHistory.populateEditText( patient.getId());
                dialog.show();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Home.this, Intermediate.class);
                startActivity(intent1);
                finish();
            }
        });
    }
    public void initializeComponents() {
        personal_info = findViewById(R.id.personal_info);
        medical_history = findViewById(R.id.medical_history);
        appointment_details = findViewById(R.id.appointment_details);
        make_appointment = findViewById(R.id.make_appointment);
        question = findViewById(R.id.question);
        consultation_price = findViewById(R.id.cost_consultation);
        dialog = new BottomSheetDialog(this);
        name = findViewById(R.id.name);
        logout = findViewById(R.id.logout);
    }

}
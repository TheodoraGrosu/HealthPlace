package com.example.healthplace;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.healthplace.Database.model.Doctor;
import com.example.healthplace.Database.model.Patient;
import com.example.healthplace.Database.retrofit.DoctorAPI;
import com.example.healthplace.Database.retrofit.PatientAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    ImageButton seePassword;
    RetrofitService retrofitService = new RetrofitService();
    PatientAPI patientAPI =  retrofitService.getRetrofit().create(PatientAPI.class);
    DoctorAPI doctorAPI = retrofitService.getRetrofit().create(DoctorAPI.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        seePassword = findViewById(R.id.seePassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailObj = String.valueOf(email.getText());
                String passwordObj = String.valueOf(password.getText());

                Call<Patient> patientCall = patientAPI.getPatientByEmail(emailObj, passwordObj);
                Log.d("PatientCall", patientCall.request().toString());

                patientCall.enqueue(new Callback<Patient>() {
                    @Override
                    public void onResponse(Call<Patient> call, Response<Patient> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(Login.this, "Thank you, login successful!", Toast.LENGTH_SHORT).show();
                            Object obj = response.body();
                            if(obj instanceof Patient){
                                Intent intent = new Intent(Login.this, Home.class);
                                intent.putExtra("patient", (Patient)obj);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else{
                            Toast.makeText(Login.this, "Sorry, login failed!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Patient> call, Throwable t) {
                        Call<Doctor> doctorCall = doctorAPI.getDoctor(emailObj, passwordObj);
                        doctorCall.enqueue(new Callback<Doctor>() {
                            @Override
                            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(Login.this, "Thank you, login successful!", Toast.LENGTH_SHORT).show();
                                    Object obj = response.body();
                                    if(obj instanceof Doctor){
                                        Intent intent = new Intent(Login.this, DoctorHome.class);
                                        intent.putExtra("doctor",(Doctor)obj);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(Login.this, "Sorry, login failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(Login.this, "Sorry, login failed!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Doctor> call, Throwable t) {

                            }
                        });
                    }
                });

            }
        });



        seePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = password.getInputType();
                if(type == InputType.TYPE_TEXT_VARIATION_PASSWORD){
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else{
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

    }

    public void resetPassword(View view) {
        Intent intent = new Intent(Login.this, ResetPassword.class);
        startActivity(intent);
        finish();
    }

}


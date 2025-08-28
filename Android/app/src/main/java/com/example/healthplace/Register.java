package com.example.healthplace;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.healthplace.Database.model.Patient;
import com.example.healthplace.Database.retrofit.PatientAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.util.logging.Level;
import java.util.logging.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    EditText last_name;
    EditText first_name;
    EditText email;
    EditText phone;
    EditText password;
    EditText repeatPassword;
    EditText nationality;
    EditText CNP;
    EditText dateBirth;
    EditText gender;
    EditText address;
    EditText city;
    EditText country;
    Button signUp;
    boolean isDataValid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        last_name = findViewById(R.id.name);
        first_name = findViewById(R.id.first_name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        repeatPassword = findViewById(R.id.repeat_password);
        nationality = findViewById(R.id.nationality);
        CNP = findViewById(R.id.CNP);
        dateBirth = findViewById(R.id.dateBirth);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        signUp = findViewById(R.id.register);
        RetrofitService retrofitService = new RetrofitService();
        PatientAPI patientAPI =  retrofitService.getRetrofit().create(PatientAPI.class);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastName = String.valueOf(last_name.getText());
                String firstName = String.valueOf(first_name.getText());
                String emailPacient = String.valueOf(email.getText());
                String phonePacient = String.valueOf(phone.getText());
                String passwordPacient = String.valueOf(password.getText());
                String repeatPasswordPacient = String.valueOf(repeatPassword.getText());
                String nationalityPacient = String.valueOf(nationality.getText());
                String CNPpacient = String.valueOf(CNP.getText().toString());
                String DateBirth = String.valueOf(dateBirth.getText());
                String genderPacient = String.valueOf(gender.getText());
                String addressPacient = String.valueOf(address.getText());
                String cityPacient = String.valueOf(city.getText());
                String countryPacient = String.valueOf(country.getText());
                if (lastName.isEmpty() || firstName.isEmpty() || emailPacient.isEmpty() ||
                        phonePacient.isEmpty() || passwordPacient.isEmpty() || repeatPasswordPacient.isEmpty() ||
                        nationalityPacient.isEmpty() || CNPpacient.isEmpty() || DateBirth.isEmpty() ||
                        genderPacient.isEmpty() || addressPacient.isEmpty() || cityPacient.isEmpty() || countryPacient.isEmpty()){
                    Toast.makeText(Register.this, "Please complete all fields!", Toast.LENGTH_LONG).show();
                }
                else{
                    if (EmailValidator.isValidEmail(emailPacient)) {
                        Toast.makeText(Register.this, "Email is valid!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Register.this, "Email is not valid!", Toast.LENGTH_SHORT).show();
                        isDataValid = false;
                    }
                    }

                    if (phonePacient.length() == 10) {
                        Toast.makeText(Register.this, "Phone number is valid!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Register.this, "Phone number is not valid!", Toast.LENGTH_SHORT).show();
                        isDataValid = false;
                    }

                    if (CNPpacient.length() == 13) {
                        Toast.makeText(Register.this, "CNP is valid!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Register.this, "CNP is not valid!", Toast.LENGTH_SHORT).show();
                        isDataValid = false;
                    }
                    if(isDataValid){

                        Patient patient = new Patient();
                        patient.setLastName(lastName);
                        patient.setFirstName(firstName);
                        patient.setEmail(emailPacient);
                        patient.setPhoneNumber(phonePacient);
                        patient.setPassword(passwordPacient);
                        patient.setNationality(nationalityPacient);
                        patient.setCNP(CNPpacient);
                        patient.setDateBirth(DateBirth);
                        patient.setGender(genderPacient);
                        patient.setAddress(addressPacient);
                        patient.setCity(cityPacient);
                        patient.setCountry(countryPacient);
                        if (passwordPacient.equals(repeatPasswordPacient)) {
                            patientAPI.save(patient).enqueue(new Callback<Patient>() {
                                @Override
                                public void onResponse(Call<Patient> call, Response<Patient> response) {
                                    Toast.makeText(Register.this, "Thank you, register successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<Patient> call, Throwable t) {
                                    Toast.makeText(Register.this, "Sorry, register failed!", Toast.LENGTH_SHORT).show();
                                    Logger.getLogger(Register.class.getName()).log(Level.SEVERE, "Error occurred", t);
                                }
                            });
                        } else {
                            Toast.makeText(Register.this, "Password do not match! Please re-enter", Toast.LENGTH_SHORT).show();
                        }

                    }
            }});
                }
}








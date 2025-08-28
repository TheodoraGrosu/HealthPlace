package com.example.healthplace;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.healthplace.Database.model.Patient;
import com.example.healthplace.Database.retrofit.PatientAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonalInfo {
    EditText lastName;
    EditText firstName;
    EditText nationality;
    EditText CNP;
    EditText dateBirth;
    EditText gender;
    EditText email;
    EditText phoneNumber;
    EditText address;
    EditText city;
    EditText country;
    TextView patientCode;
    RetrofitService retrofitService = new RetrofitService();
    PatientAPI patientAPI =  retrofitService.getRetrofit().create(PatientAPI.class);
    public static Patient patientUpdate;


    public void setPatientInfo(Patient patient) {
        lastName.setText(patient.getLastName());
        firstName.setText(patient.getFirstName());
        nationality.setText(patient.getNationality());
        Log.d("Response", patient.toString());
       // CNP.setText(String.valueOf(patient.getCNP()));
        dateBirth.setText(patient.getDateBirth());
        gender.setText(patient.getGender());
        email.setText(patient.getEmail());
        phoneNumber.setText(patient.getPhoneNumber());
        address.setText(patient.getAddress());
        city.setText(patient.getCity());
        country.setText(patient.getCountry());
        patientCode.setText(String.valueOf(patient.getId()));
    }

    public void initializeComponents(View view) {

        lastName = view.findViewById(R.id.lastName);
        firstName = view.findViewById(R.id.firstName);
        nationality = view.findViewById(R.id.nationality);
        CNP = view.findViewById(R.id.CNP);
        dateBirth = view.findViewById(R.id.dateBirth);
        gender = view.findViewById(R.id.gender);
        email = view.findViewById(R.id.emailP);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        address = view.findViewById(R.id.address);
        city = view.findViewById(R.id.city);
        country = view.findViewById(R.id.country);
        patientCode = view.findViewById(R.id.tvCode);
    }

    public void updatePatient(Patient patient){
        patientUpdate = patient;
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String emailUpdate = email.getText().toString();
                    patientUpdate.setEmail(emailUpdate);
                    email.setText(emailUpdate);
                    Map<String, String> updateParams = new HashMap<>();
                    updateParams.put("field", "email");
                    updateParams.put("value", emailUpdate);
                    patientAPI.updatePatient(patient.getId(), updateParams).enqueue(new Callback<Patient>() {
                        @Override
                        public void onResponse(Call<Patient> call, Response<Patient> response) {
                            if (response.isSuccessful()) {
                                Log.d("update", "Patient updated successfully");
                                Patient patient1 = response.body();
                                setPatientInfo(patient1);
                            } else {
                                Log.e("update", "Failed to update patient: " + response.message());
                            }
                        }
                        @Override
                        public void onFailure(Call<Patient> call, Throwable t) {
                            Log.e("update", "Error updating patient: " + t.getMessage());
                        }
                    });
                }
            }
        });

        phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String phoneUpdate = phoneNumber.getText().toString();
                    patientUpdate.setPhoneNumber(phoneUpdate);
                    phoneNumber.setText(phoneUpdate);
                    Map<String, String> updateParams = new HashMap<>();
                    updateParams.put("field", "phoneNumber");
                    updateParams.put("value", phoneUpdate);
                    patientAPI.updatePatient(patient.getId(), updateParams).enqueue(new Callback<Patient>() {
                        @Override
                        public void onResponse(Call<Patient> call, Response<Patient> response) {
                            if (response.isSuccessful()) {
                                Log.d("update", "Patient updated successfully");
                                Patient patient1 = response.body();
                                setPatientInfo(patient1);
                            } else {
                                Log.e("update", "Failed to update patient: " + response.message());
                            }
                        }
                        @Override
                        public void onFailure(Call<Patient> call, Throwable t) {
                            Log.e("update", "Error updating patient: " + t.getMessage());
                        }
                    });
                }
            }
        });

        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String addressUpdate = address.getText().toString();
                    patientUpdate.setAddress(addressUpdate);
                    address.setText(addressUpdate);
                    Map<String, String> updateParams = new HashMap<>();
                    updateParams.put("field", "address");
                    updateParams.put("value", addressUpdate);
                    patientAPI.updatePatient(patient.getId(), updateParams).enqueue(new Callback<Patient>() {
                        @Override
                        public void onResponse(Call<Patient> call, Response<Patient> response) {
                            if (response.isSuccessful()) {
                                Patient patient1 = response.body();
                                setPatientInfo(patient1);
                                Log.d("update", "Patient updated successfully");
                            } else {
                                Log.e("update", "Failed to update patient: " + response.message());
                            }
                        }
                        @Override
                        public void onFailure(Call<Patient> call, Throwable t) {
                            Log.e("update", "Error updating patient: " + t.getMessage());
                        }
                    });
                }
            }
        });

        city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String cityUpdate = city.getText().toString();
                    patientUpdate.setCity(cityUpdate);
                    city.setText(cityUpdate);
                    Map<String, String> updateParams = new HashMap<>();
                    updateParams.put("field", "city");
                    updateParams.put("value", cityUpdate);
                    patientAPI.updatePatient(patient.getId(), updateParams).enqueue(new Callback<Patient>() {
                        @Override
                        public void onResponse(Call<Patient> call, Response<Patient> response) {
                            if (response.isSuccessful()) {
                                Log.d("update", "Patient updated successfully");
                                Patient patient1 = response.body();
                                setPatientInfo(patient1);
                            } else {
                                Log.e("update", "Failed to update patient: " + response.message());
                            }
                        }
                        @Override
                        public void onFailure(Call<Patient> call, Throwable t) {
                            Log.e("update", "Error updating patient: " + t.getMessage());
                        }
                    });
                }
            }
        });

        country.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String countryUpdate = country.getText().toString();
                    patientUpdate.setCountry(countryUpdate);
                    country.setText(countryUpdate);
                    Map<String, String> updateParams = new HashMap<>();
                    updateParams.put("field", "country");
                    updateParams.put("value", countryUpdate);
                    patientAPI.updatePatient(patient.getId(), updateParams).enqueue(new Callback<Patient>() {
                        @Override
                        public void onResponse(Call<Patient> call, Response<Patient> response) {
                            if (response.isSuccessful()) {
                                Log.d("update", "Patient updated successfully");
                                Patient patient1 = response.body();
                                setPatientInfo(patient1);
                            } else {
                                Log.e("update", "Failed to update patient: " + response.message());
                            }
                        }
                        @Override
                        public void onFailure(Call<Patient> call, Throwable t) {
                            Log.e("update", "Error updating patient: " + t.getMessage());
                        }
                    });
                }
            }
        });

    }
}
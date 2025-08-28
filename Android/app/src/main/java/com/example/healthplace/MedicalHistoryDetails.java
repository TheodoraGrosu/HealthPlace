package com.example.healthplace;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import com.example.healthplace.Database.model.MedicalHistory;
import com.example.healthplace.Database.retrofit.MedicalHistoryAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalHistoryDetails {
    EditText allergies;
    EditText chronicDiseases;
    EditText surgeries;
    EditText immunizations;
    RetrofitService retrofitService = new RetrofitService();
    MedicalHistoryAPI medicalHistoryAPI = retrofitService.getRetrofit().create(MedicalHistoryAPI.class);

    public void initializeComponents(View view){
        allergies = view.findViewById(R.id.allergiesIntolerances);
        chronicDiseases = view.findViewById(R.id.chronicDiseases);
        surgeries = view.findViewById(R.id.proceduresPerformed);
        immunizations = view.findViewById(R.id.immunization);
    }

    public void populateEditText(int idPatient){
        Call<MedicalHistory> medicalHistoryCall = medicalHistoryAPI.getMedicalHistory(idPatient);
        medicalHistoryCall.enqueue(new Callback<MedicalHistory>() {
            @Override
            public void onResponse(Call<MedicalHistory> call, Response<MedicalHistory> response) {
                if(response.isSuccessful()){
                    MedicalHistory medicalHistory = response.body();
                    allergies.setEnabled(false);
                    allergies.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    allergies.setLines(5);
                    allergies.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    chronicDiseases.setEnabled(false);
                    chronicDiseases.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    chronicDiseases.setLines(5);
                    chronicDiseases.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    surgeries.setEnabled(false);
                    surgeries.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    surgeries.setLines(5);
                    surgeries.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    immunizations.setEnabled(false);
                    immunizations.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    immunizations.setLines(5);
                    immunizations.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    if(medicalHistory.getAllergies() != null || medicalHistory.getIntolerances() != null)
                        allergies.setText(String.join(",",medicalHistory.getAllergies())+"\n"+String.join(",",medicalHistory.getIntolerances()));
                    else{
                        allergies.setText("");
                    }
                    if(medicalHistory.getChronicDiseases() != null)
                        chronicDiseases.setText( String.join(",",medicalHistory.getChronicDiseases()));
                    else{
                        chronicDiseases.setText("");
                    }
                    if(medicalHistory.getSurgeries() != null){
                        surgeries.setText(String.join(",",medicalHistory.getSurgeries()));
                    }
                    else{
                        surgeries.setText("");
                    }
                    if(medicalHistory.getImmunizations() != null){
                        immunizations.setText(String.join(",",medicalHistory.getImmunizations()));
                    }
                    else {
                        immunizations.setText("");
                    }
                }
            }

            @Override
            public void onFailure(Call<MedicalHistory> call, Throwable t) {

            }
        });
    }
}
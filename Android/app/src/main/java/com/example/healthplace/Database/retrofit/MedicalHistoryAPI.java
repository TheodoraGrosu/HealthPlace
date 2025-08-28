package com.example.healthplace.Database.retrofit;

import com.example.healthplace.Database.model.MedicalHistory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MedicalHistoryAPI {

    @GET("/getMedicalHistory/{idPatient}")
    Call<MedicalHistory> getMedicalHistory(@Path("idPatient") int idPatient);
}

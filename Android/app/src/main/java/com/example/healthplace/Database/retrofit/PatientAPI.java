package com.example.healthplace.Database.retrofit;

import com.example.healthplace.Database.model.Patient;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface PatientAPI {
    @GET("/patient/get-all")
    Call<List<Patient>> getAllPatients();

    @POST("/patient/save")
    Call<Patient> save(@Body Patient patient);

    @FormUrlEncoded
    @POST("/patient/getPatient/")
    Call<Patient> getPatientByEmail(@Field("email") String email, @Field("password") String password);

    @PUT("/patient/update/{id}")
    Call<Patient> updatePatient(@Path("id") int id, @QueryMap Map<String, String> updateParams);

    @GET("/patient/getById/{id}")
    Call<Patient> getById(@Path("id") int id);


    @GET("/patient/getName/{id}")
    Call<List<String>> getPatientName(@Path("id") int id);
}

package com.example.healthplace.Database.retrofit;

import com.example.healthplace.Database.model.Doctor;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DoctorAPI {
    @GET("/getAllDoctors")
    Call<List<Doctor>> getAllDoctors();

    @GET("/getWorkProgram/{id}")
    Call<ArrayList<String>> getWorkProgram(@Path("id") int id);

    @GET("/getDays/{id}")
    Call<List<String>> getDays(@Path("id") int id);

    @FormUrlEncoded
    @POST("/doctor/getDoctor")
    Call<Doctor> getDoctor(@Field("email") String email, @Field("password") String password);

    @GET("/doctor/getName/{id}")
    Call<List<String>> getDoctorName(@Path("id") int id);
}

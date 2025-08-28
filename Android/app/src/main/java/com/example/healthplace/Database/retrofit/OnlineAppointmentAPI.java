package com.example.healthplace.Database.retrofit;

import com.example.healthplace.Database.model.OnlineAppointment;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OnlineAppointmentAPI {

    @POST("/appointment/save")
    Call<Void> saveAppointment(@Body OnlineAppointment onlineAppointment);

    @GET("/appointmentForPatient/{id}")
    Call<List<OnlineAppointment>> getAppointmentsForPatient(@Path("id") int id);

    @DELETE("/appointment/delete/{idAppointment}")
    Call<Void> deleteAppointment(@Path("idAppointment") int idAppointment);

    @GET("/appointmentForDoctor/{id}")
    Call<List<OnlineAppointment>> getAppointmentsForDoctor(@Path("id") int id);

    @GET("/appointmentForDoctorRecycler/{id}")
    Call<List<OnlineAppointment>> getAppointmentsForDoctorRecycler(@Path("id") int id);

    @POST("/appointmentConfirm/save")
    Call<Void> appointmentConfirm(@Body int id_appointment);

    @GET("/getHoursReserved/{idDoctor}")
    Call<List<String>> getHoursReserved(@Path("idDoctor") int idDoctor);
}

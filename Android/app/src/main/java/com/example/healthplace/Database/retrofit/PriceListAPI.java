package com.example.healthplace.Database.retrofit;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PriceListAPI {
    @GET("/getDepartments")
    Call<List<String>> getDepartment();

    @GET("/getServices")
    Call<Map<String,List<String>>> getServices();

    @FormUrlEncoded
    @POST("/getPrices")
    Call<Map<String, Float>> getPricesConsultation(@Field("department") String department,@Field("service") String service);
}

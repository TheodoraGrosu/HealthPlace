package com.example.healthplace;

import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.healthplace.Database.retrofit.PriceListAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultationPrice {
    Spinner department;
    Spinner service;
    CheckBox cbTicket;
    ConstraintLayout layout;
    Button findPrice;
    private static String departSelected;
    private static String serviceSelected;
    TextView fullPriceTV;
    TextView priceTicketTV;
    RetrofitService retrofitService = new RetrofitService();
    PriceListAPI priceListAPI = retrofitService.getRetrofit().create(PriceListAPI.class);


    public void initComponents(View view){
        department = view.findViewById(R.id.spinnerDepartmentPrice);
        service = view.findViewById(R.id.spinnerServicePrice);
        cbTicket = view.findViewById(R.id.cbTicket);
        findPrice = view.findViewById(R.id.findPrice);
        layout = view.findViewById(R.id.layoutPrice);
        layout.setVisibility(View.INVISIBLE);
        fullPriceTV = view.findViewById(R.id.tvFullPrice);
        priceTicketTV = view.findViewById(R.id.tvTicketPrice);
    }

    public void populateSpinner(Context context) {

        Call<List<String>> departments = priceListAPI.getDepartment();
        departments.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    ArrayList<String> departmentsArray = (ArrayList<String>) response.body();
                    departmentsArray.add(0, "");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, departmentsArray);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    department.setAdapter(adapter);
                    department.setSelection(0);

                    department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        private boolean departmentSelected = false;

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (!departmentSelected) {
                                departmentSelected = true;
                                return;
                            }
                            departSelected = parent.getItemAtPosition(position).toString();

                            Call<Map<String, List<String>>> services = priceListAPI.getServices();
                            services.enqueue(new Callback<Map<String, List<String>>>() {
                                @Override
                                public void onResponse(Call<Map<String, List<String>>> call, Response<Map<String, List<String>>> response) {
                                    if (response.isSuccessful()) {
                                        Map<String, List<String>> servicesArray = (Map<String, List<String>>) response.body();
                                        ArrayList<String> servicesList = (ArrayList<String>) servicesArray.get(departSelected);
                                        if (servicesList != null) {
                                            Log.d("Response", servicesList.toString());
                                            servicesList.add(0, "");
                                            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, servicesList);
                                            adapter.setDropDownViewResource(R.layout.spinner_item);
                                            service.setAdapter(adapter);
                                            service.setSelection(0);
                                            service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    serviceSelected = parent.getItemAtPosition(position).toString();
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });

                                        } else {
                                            Log.d("Response", "ServicesList is null");
                                        }
//
                                    } else {
                                        Log.d("Response Error", "Response unsuccessful. Code: " + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Map<String, List<String>>> call, Throwable t) {

                                }
                            });
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });


    }

    public void showPrice(Context context){
        findPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(department.getSelectedItemPosition() == 0 || service.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, "Please select a department and a service", Toast.LENGTH_LONG).show();
                }
                else{

                    layout.setVisibility(View.VISIBLE);
                    Call<Map<String, Float>> prices = priceListAPI.getPricesConsultation(departSelected, serviceSelected);
                    prices.enqueue(new Callback<Map<String, Float>>() {
                        @Override
                        public void onResponse(Call<Map<String, Float>> call, Response<Map<String, Float>> response) {
                            if (response.isSuccessful()) {
                                Map<String, Float> pricesMap = (Map<String, Float>) response.body();
                                if (pricesMap != null) {
                                    fullPriceTV.setText(String.valueOf(pricesMap.get("fullPrice")));
                                    if (cbTicket.isChecked()) {
                                        float fullPrice = pricesMap.get("fullPrice");
                                        float discountTicket = pricesMap.get("discountTicket");
                                        float priceTicket = fullPrice - (fullPrice * discountTicket / 100);
                                        priceTicketTV.setText(String.valueOf(priceTicket));
                                    } else {
                                        priceTicketTV.setText(String.valueOf(pricesMap.get("fullPrice")));
                                    }
                                } else {
                                    Log.d("Response", "PricesMap is null");
                                }
                            } else {
                                Log.d("Response Error", "Response unsuccessful. Code: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Map<String, Float>> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}
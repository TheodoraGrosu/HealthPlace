package com.example.healthplace;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.healthplace.Database.model.Doctor;
import com.example.healthplace.Database.model.OnlineAppointment;
import com.example.healthplace.Database.retrofit.DoctorAPI;
import com.example.healthplace.Database.retrofit.OnlineAppointmentAPI;
import com.example.healthplace.Database.retrofit.PriceListAPI;
import com.example.healthplace.Database.retrofit.RetrofitService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeAppointments {
    private static OnlineAppointment onlineAppointment = new OnlineAppointment();
    private static String departSelected;
    Spinner department;
    Spinner service;
    Spinner doctor;
    Spinner time;
    EditText date;
    Button saveButton;
    RetrofitService retrofitService = new RetrofitService();
    PriceListAPI priceListAPI = retrofitService.getRetrofit().create(PriceListAPI.class);
    DoctorAPI doctorAPI = retrofitService.getRetrofit().create(DoctorAPI.class);
    OnlineAppointmentAPI onlineAppointmentAPI = retrofitService.getRetrofit().create(OnlineAppointmentAPI.class);
    ArrayList<Doctor> doctorsArray = new ArrayList<>();
    static List<String> hoursAvailable = new ArrayList<>();
    static List<String> hoursReserved = new ArrayList<>();


    public void initializeComponents(View view) {
        department = view.findViewById(R.id.spinnerSpecialty);
        service = view.findViewById(R.id.spinnerServices);
        doctor = view.findViewById(R.id.spinnerDoctors);
        time = view.findViewById(R.id.spinnerTime);
        date = view.findViewById(R.id.editTextDate);
        saveButton = view.findViewById(R.id.save);
    }

    public void populateSpinner(Context context) {
        doctorsArray.add(0, new Doctor());
        DoctorAdapter doctorAdapter = new DoctorAdapter(context, doctorsArray);
        doctor.setAdapter(doctorAdapter);
        doctor.setSelection(0);

        Call<List<String>> departments = priceListAPI.getDepartment();
        departments.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    ArrayList<String> departmentsArray = (ArrayList<String>) response.body();
                    departmentsArray.add(0, "");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, departmentsArray);
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
                            onlineAppointment.setDepartment(departSelected);

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
                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, servicesList);
                                            adapter.setDropDownViewResource(R.layout.spinner_item);
                                            service.setAdapter(adapter);
                                            service.setSelection(0);

                                            service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    String service = (String)parent.getItemAtPosition(position);
                                                    onlineAppointment.setService(service);
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


                            Call<List<Doctor>> doctors = doctorAPI.getAllDoctors();
                            doctors.enqueue(new Callback<List<Doctor>>() {
                                @Override
                                public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                                    if (response.isSuccessful()) {
                                        List<Doctor> doctorsList = response.body();
                                        for (Doctor doctor : doctorsList) {
                                            if (doctor.getDepartment().equals(departSelected))
                                                doctorsArray.add(doctor);
                                        }
                                        Log.d("Response", doctorsArray.toString());
                                        doctor.setAdapter(doctorAdapter);
                                        doctor.setSelection(0);
                                        doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                Doctor selectedDoctor = (Doctor) parent.getItemAtPosition(position);
                                                int idDoctor = selectedDoctor.getId();
                                                onlineAppointment.setIdDoctor(idDoctor);
                                                Log.d("Response", selectedDoctor.toString());
                                                Log.d("Response", String.valueOf(idDoctor));
                                                getHours(idDoctor, context);
                                                getDay(idDoctor, context);

                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {}
                                        });
                                    } else {
                                        Log.d("Response Error", "Response unsuccessful. Code: " + response.code());
                                    }
                                }
                                @Override
                                public void onFailure(Call<List<Doctor>> call, Throwable t) {

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

    public void getHours(int idDoctor, Context context) {
        getHoursReserved(idDoctor);
        Call<ArrayList<String>> workingHours = doctorAPI.getWorkProgram(idDoctor);
        workingHours.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (response.isSuccessful()) {
                    ArrayList<String> workingHoursArray = response.body();
                    Log.d("Response", workingHoursArray.toString());
                    hoursAvailable.clear();
                    for(String hour : workingHoursArray) {
                        if (!hoursReserved.contains(hour)) {
                            hoursAvailable.add(hour);
                        }
                    }

                    hoursAvailable.add(0, "");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, hoursAvailable);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    time.setAdapter(adapter);
                    time.setSelection(0);
                    time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String hour = (String)parent.getItemAtPosition(position);
                            onlineAppointment.setTime(hour);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    Log.d("Response", "Response unsuccessful. Code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });
    }

    public void getHoursReserved(int idDoctor){
        Call<List<String>> hoursCall = onlineAppointmentAPI.getHoursReserved(idDoctor);
        hoursCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()){
                    hoursReserved = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }


    public void getDay(int idDoctor, Context context)
    {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(context, idDoctor);

            }
        });
    }

    private void showDatePickerDialog(Context context, int idDoctor) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Data selectată de utilizator
                Call<List<String>> days = doctorAPI.getDays(idDoctor);
                days.enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if(response.isSuccessful())
                        {
                            List<String> daysArray = response.body();
                            Log.d("Response", daysArray.toString());
                            try {
                                String day = setDayOfWeek(dayOfMonth, month+1, year);
                                if(daysArray.contains(String.valueOf(day)))
                                {
                                    date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                                    onlineAppointment.setDate(date.getText().toString());
                                    Log.d("Response", "Day is: " + day);
                                }
                                else
                                {
                                    Log.d("Response", "Day is not in the list");
                                    Toast.makeText(context, "The doctor is not available!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                        else
                        {
                            Log.d("Response", "Response unsuccessful. Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {

                    }
                });
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public String setDayOfWeek(int day, int month, int year) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date selectedDate = dateFormat.parse(day+"/"+month+"/"+year);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String dayOfWeekString;
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                dayOfWeekString = "Duminica";
                return dayOfWeekString;
            case Calendar.MONDAY:
                dayOfWeekString = "Luni";
                return dayOfWeekString;
            case Calendar.TUESDAY:
                dayOfWeekString = "Marti";
                return dayOfWeekString;
            case Calendar.WEDNESDAY:
                dayOfWeekString = "Miercuri";
                return dayOfWeekString;
            case Calendar.THURSDAY:
                dayOfWeekString = "Joi";
                return dayOfWeekString;
            case Calendar.FRIDAY:
                dayOfWeekString = "Vineri";
                return dayOfWeekString;
            case Calendar.SATURDAY:
                dayOfWeekString = "Sambata";
                return dayOfWeekString;
            default:
                dayOfWeekString = "N/A";
                return dayOfWeekString;
        }
    }


    public void save(Context context, int idPatient){
        onlineAppointment.setIdPatient(idPatient);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (department.getSelectedItemPosition() == 0 || service.getSelectedItemPosition() == 0 || doctor.getSelectedItemPosition() == 0 || time.getSelectedItemPosition() == 0 || date.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please fill in all the fields!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    onlineAppointmentAPI.saveAppointment(onlineAppointment).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("Response", "Appointment saved successfully!");
                                Toast.makeText(context, "Appointment saved successfully!", Toast.LENGTH_SHORT).show();
                                department.setSelection(0);
                                service.setSelection(0);
                                doctor.setSelection(0);
                                time.setSelection(0);
                                date.setText("");
                            } else {
                                Log.d("Response", "Response unsuccessful. Code: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("Response", "Response failed. Error: " + t.getMessage());
                        }
                    });
                }
            }
        });

    }
}



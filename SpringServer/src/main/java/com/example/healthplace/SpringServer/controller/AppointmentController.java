package com.example.healthplace.SpringServer.controller;

import com.example.healthplace.SpringServer.model.AppointmentModel.AppointmentDao;
import com.example.healthplace.SpringServer.model.AppointmentModel.OnlineAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AppointmentController {
    @Autowired
    AppointmentDao appointmentDao;

    @PostMapping("/appointment/save")
    public void saveAppointment(@RequestBody OnlineAppointment appointment){
        appointmentDao.saveAppointment(appointment);
    }

    @GetMapping("/appointment/getAll")
    public List<OnlineAppointment> getAllOnlineAppointment(){
        return appointmentDao.getAllAppointment();
    }

    @GetMapping("/appointmentForPatient/{id}")
    public List<OnlineAppointment> getAppointmentForPatient(@PathVariable int id){
        return appointmentDao.getAppointmentsForPatient(id);
    }

    @DeleteMapping("/appointment/delete/{idAppointment}")
    public void deleteAppointment(@PathVariable int idAppointment){
        appointmentDao.deleteAppointment(idAppointment);
    }


    @GetMapping("/appointmentForDoctor/{id}")
    public List<OnlineAppointment> getAppointmentForDoctor(@PathVariable int id){
        return appointmentDao.getAppointmentsForDoctor(id);
    }

    @GetMapping("/appointmentForDoctorRecycler/{id}")
    public List<OnlineAppointment> getAppointmentForDoctorRecycler(@PathVariable int id){
        return appointmentDao.getAppointmentsForDoctorRecycler(id);
    }

    @PostMapping("/appointmentConfirm/save")
    public void confirmAppointment(@RequestBody int id_appointment){
        appointmentDao.confirmAppointment(id_appointment);
    }

    @GetMapping("/getHoursReserved/{idDoctor}")
    public List<String> getHoursReserved(@PathVariable int idDoctor){
        return appointmentDao.getHoursReserved(idDoctor);
    }
}

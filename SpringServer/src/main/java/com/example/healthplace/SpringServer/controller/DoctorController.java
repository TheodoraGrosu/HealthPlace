package com.example.healthplace.SpringServer.controller;

import com.example.healthplace.SpringServer.model.DoctorModel.Doctor;
import com.example.healthplace.SpringServer.model.DoctorModel.DoctorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DoctorController {
    @Autowired
    DoctorDao doctorDao;

    @GetMapping("/getAllDoctors")
    public List<Doctor> getAllDoctors(){
        return doctorDao.getAllDoctors();
    }

    @GetMapping("/getWorkProgram/{id}")
    public ArrayList<String> getWorkProgram(@PathVariable int id){
        return doctorDao.getWorkProgram(id);
    }

    @GetMapping("/getDays/{id}")
    public List<String> getDays(@PathVariable int id){
        return doctorDao.getDaysWorking(id);
    }

    @PostMapping("/doctor/getDoctor")
    public ResponseEntity<Doctor> getDoctor(@RequestParam("email") String email, @RequestParam("password") String password){
        return ResponseEntity.ok(doctorDao.getDoctor(email, password));
    }

    @GetMapping("/doctor/getName/{id}")
    public List<String> getNameDoctor(@PathVariable int id){
        return doctorDao.getDoctorName(id);
    }
}

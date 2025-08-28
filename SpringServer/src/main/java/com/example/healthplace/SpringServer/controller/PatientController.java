package com.example.healthplace.SpringServer.controller;

import com.example.healthplace.SpringServer.model.PatientModel.Patient;
import com.example.healthplace.SpringServer.model.PatientModel.PatientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    PatientDao patientDao;
    @GetMapping("/patient/get-all")
    public List<Patient> getAllPatient(){
        return patientDao.getAllPatients();
    }

    @PostMapping("/patient/save")
    public Patient savePatient(@RequestBody Patient patient){
        return patientDao.save(patient);
    }


    @PostMapping("/patient/getPatient/")
    public ResponseEntity<Patient> getPatientByEmail(@RequestParam("email") String email, @RequestParam("password") String password){
        return ResponseEntity.ok(patientDao.getPatient(email, password));
    }

    @PutMapping("/patient/update/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable int id, @RequestParam("field") String field, @RequestParam("value") String value){
        return ResponseEntity.ok(patientDao.updateInfo(id, field, value));
    }

    @GetMapping("/patient/getName/{id}")
    public List<String> getNamePatient(@PathVariable int id){
        return patientDao.getPatientName(id);
    }
}

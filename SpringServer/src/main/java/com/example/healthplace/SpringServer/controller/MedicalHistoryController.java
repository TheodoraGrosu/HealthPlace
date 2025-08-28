package com.example.healthplace.SpringServer.controller;

import com.example.healthplace.SpringServer.MedicalHistoryModel.MedicalHistory;
import com.example.healthplace.SpringServer.MedicalHistoryModel.MedicalHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryDao medicalHistoryDao;

    @GetMapping("/getMedicalHistory/{idPatient}")
    public MedicalHistory getMedicalHistory(@PathVariable int idPatient){
        return medicalHistoryDao.getMedicalHistory(idPatient);
    }
}

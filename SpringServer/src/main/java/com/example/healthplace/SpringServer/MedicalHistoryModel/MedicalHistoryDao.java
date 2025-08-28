package com.example.healthplace.SpringServer.MedicalHistoryModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalHistoryDao {

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    public MedicalHistory getMedicalHistory(int idPatient){
        return medicalHistoryRepository.findByidPatient(idPatient);
    }
}

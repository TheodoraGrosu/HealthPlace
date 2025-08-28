package com.example.healthplace.SpringServer.MedicalHistoryModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalHistoryRepository  extends JpaRepository<MedicalHistory, Integer> {
    MedicalHistory findByidPatient(int idPatient);
}

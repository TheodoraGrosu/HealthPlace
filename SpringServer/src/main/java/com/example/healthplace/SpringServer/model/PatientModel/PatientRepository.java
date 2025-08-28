package com.example.healthplace.SpringServer.model.PatientModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByEmailAndPassword(String email, String password);
}

package com.example.healthplace.SpringServer.model.DoctorModel;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findById(Integer id);
    Optional<Doctor> findByEmailAndPassword(String email, String password);
}

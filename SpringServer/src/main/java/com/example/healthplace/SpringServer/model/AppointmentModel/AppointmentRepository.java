package com.example.healthplace.SpringServer.model.AppointmentModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<OnlineAppointment, Integer> {
    List<OnlineAppointment> findByidDoctor(int idDoctor);
}

package com.example.healthplace.SpringServer.model.AppointmentModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentDao {
    @Autowired
    private AppointmentRepository repository;

    public List<OnlineAppointment> getAllAppointment(){
        List<OnlineAppointment> onlineAppointments = new ArrayList<>();
        Streamable.of(repository.findAll()).forEach(appointment -> {
            onlineAppointments.add(appointment);
        });
        return  onlineAppointments;
    }

    public void saveAppointment(OnlineAppointment appointment){
        OnlineAppointment onlineAppointment = OnlineAppointment.builder()
                .idPatient(appointment.getIdPatient())
                .idDoctor(appointment.getIdDoctor())
                .service(appointment.getService())
                .date(appointment.getDate())
                .time(appointment.getTime())
                .department(appointment.getDepartment())
                .is_confirmed(false)
                .build();
        repository.save(onlineAppointment);
    }

    public List<OnlineAppointment> getAppointmentsForPatient(int idPatient){
        List<OnlineAppointment> onlineAppointments = new ArrayList<>();
        Streamable.of(repository.findAll()).forEach(appointment -> {
            if(appointment.getIdPatient() == idPatient && appointment.isConfirmed() == true){
                onlineAppointments.add(appointment);
            }
        });
        return  onlineAppointments;
    }

    public void deleteAppointment(int idAppointment){
         repository.deleteById(idAppointment);
    }


    public List<OnlineAppointment> getAppointmentsForDoctor(int idDoctor){
        List<OnlineAppointment> onlineAppointments = new ArrayList<>();
        Streamable.of(repository.findAll()).forEach(appointment -> {
            if(appointment.getIdDoctor() == idDoctor && appointment.isConfirmed() == true){
                onlineAppointments.add(appointment);
            }
        });
        return  onlineAppointments;
    }

    public List<OnlineAppointment> getAppointmentsForDoctorRecycler(int idDoctor){
        List<OnlineAppointment> onlineAppointments = new ArrayList<>();
        Streamable.of(repository.findAll()).forEach(appointment -> {
            if(appointment.getIdDoctor() == idDoctor ){
                onlineAppointments.add(appointment);
            }
        });
        return  onlineAppointments;
    }

    public void confirmAppointment(int idAppointment){
        OnlineAppointment onlineAppointment = repository.findById(idAppointment).get();
        onlineAppointment.setConfirmed(true);
        repository.save(onlineAppointment);
    }

    public List<String> getHoursReserved(int idDoctor){
        List<String> hoursList = new ArrayList<>();
        List<OnlineAppointment> list = repository.findByidDoctor(idDoctor);
        for(OnlineAppointment onlineAppointment : list){
            hoursList.add(onlineAppointment.getTime());
        }
        return  hoursList;
    }
}

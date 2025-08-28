package com.example.healthplace.SpringServer.model.PatientModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PatientDao {

    private final static Logger LOG = LoggerFactory.getLogger(com.example.healthplace.SpringServer.model.PatientModel.PatientDao.class);
    @Autowired
    private PatientRepository repository;

    public Patient save(Patient patientRequest){
        Patient patient = Patient.builder()
                .lastName(patientRequest.getLastName())
                .firstName(patientRequest.getFirstName())
                .CNP(patientRequest.getCNP())
                .address(patientRequest.getAddress())
                .city(patientRequest.getCity())
                .gender(patientRequest.getGender())
                .country(patientRequest.getCountry())
                .dateBirth(patientRequest.getDateBirth())
                .phoneNumber(patientRequest.getPhoneNumber())
                .nationality(patientRequest.getNationality())
                .email(patientRequest.getEmail())
                .password(patientRequest.getPassword())
                .build();
        return repository.save(patient);
    }

    public void delete(Patient patient){
        repository.delete(patient);
    }

    public List<Patient> getAllPatients(){
        List<Patient> patients = new ArrayList<>();
        Streamable.of(repository.findAll()).forEach(patient -> {
            patients.add(patient);
        });
        return  patients;
    }

    public  Patient getPatient(String email, String password){
        var pacient = repository.findByEmailAndPassword(email, password).orElse(null);
        return pacient;
    }

    public Patient updateInfo(int id, String field, String value) {
        Optional<Patient> patient = repository.findById(id);
        if(patient.isPresent()){
            Patient pacientUpdated = patient.get();
            if(field.equals("email")){
                pacientUpdated.setEmail(value);
            } else if (field.equals("phoneNumber")){
                pacientUpdated.setPhoneNumber(value);
            } else if(field.equals("address")){
                pacientUpdated.setAddress(value);
            } else if(field.equals("city")){
                pacientUpdated.setCity(value);
            } else if(field.equals("country")){
                pacientUpdated.setCountry(value);
            }
            else{
                throw new IllegalArgumentException("Field not found");
            }
            return repository.save(pacientUpdated);
        }
        else{
            throw new IllegalArgumentException("Patient not found");
        }
    }

    public List<String> getPatientName(int id){
        Optional<Patient> patient = repository.findById(id);
        if(patient.isPresent()){
            List<String> fullName = new ArrayList<>();
            fullName.add(patient.get().getLastName());
            fullName.add(patient.get().getFirstName());
            return fullName;
        }
        else{
            throw new IllegalArgumentException("Patient not found");
        }
    }
}



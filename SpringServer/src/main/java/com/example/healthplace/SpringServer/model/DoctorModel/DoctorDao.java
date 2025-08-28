package com.example.healthplace.SpringServer.model.DoctorModel;

import com.example.healthplace.SpringServer.model.PatientModel.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorDao {

    @Autowired
    private DoctorRepository repository;

    public Doctor saveDoctor(Doctor doctor){
        Doctor doctor1 = Doctor.builder()
                .lastName(doctor.getLastName())
                .firstName(doctor.getFirstName())
                .email(doctor.getEmail())
                .address(doctor.getAddress())
                .department(doctor.getDepartment())
                .speciality(doctor.getSpeciality())
                .salary(doctor.getSalary())
                .employmentDate(doctor.getEmploymentDate())
                .build();
        return repository.save(doctor1);
    }

    public List<Doctor> getAllDoctors(){
        List<Doctor> doctors = new ArrayList<>();
        Streamable.of(repository.findAll()).forEach(doctor -> {
            doctors.add(doctor);
        });
        return doctors;
    }

    public ArrayList<String> getWorkProgram(int id){
        Doctor doctor = repository.findById(id).orElse(null);
        String workProgram = doctor.getWorkProgram();
        String[] workProgramArray = workProgram.split(" - ");
        String hourStart = workProgramArray[0];
        String hourEnd = workProgramArray[1];
        int hourStartInt = Integer.parseInt(hourStart.split(":")[0]);
        int hourEndInt = Integer.parseInt(hourEnd.split(":")[0]);
        ArrayList<Integer> hourList = new ArrayList<>();
        for (int i = hourStartInt; i <= hourEndInt; i++) {
            hourList.add(i);
        }
        ArrayList<String> hoursListString = new ArrayList<>();
        for (int number : hourList) {
            String formattedTime;
            if (number < 10) {
                formattedTime = String.format("%02d:00", number);
            } else {
                formattedTime = String.format("%d:00", number);
            }
            hoursListString.add(formattedTime);
        }
        return hoursListString;

    }


    public List<String> getDaysWorking(int id){
        Doctor doctor = repository.findById(id).orElse(null);
        String dayWorking = doctor.getWorkProgramDays();
        dayWorking = dayWorking.trim();
        String[] days = dayWorking.split("-");
        ArrayList<String> daysOfWeek = new ArrayList<>();
        if (days.length == 2) {
            String dayStart = days[0].trim();
            String dayEnd = days[1].trim();
            int startIndex = getDayIndex(dayStart);
            int endIndex = getDayIndex(dayEnd);

            for (int i = startIndex; i <= endIndex; i++) {
                String zi = getDayOfWeek(i);
                daysOfWeek.add(zi);
            }
        }
        return daysOfWeek;
    }

    private static int getDayIndex(String day) {
        String[] daysOfWeek = {"Luni", "Marti", "Miercuri", "Joi", "Vineri", "Sambata", "Duminica"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            if (daysOfWeek[i].equalsIgnoreCase(day)) {
                return i;
            }
        }
        return -1;
    }

    private static String getDayOfWeek(int index) {
        String[] daysOfWeek = {"Luni", "Marti", "Miercuri", "Joi", "Vineri", "Sambata", "Duminica"};
        if (index >= 0 && index < daysOfWeek.length) {
            return daysOfWeek[index];
        }
        return "";
    }

    public  Doctor getDoctor(String email, String password){
        var doctor = repository.findByEmailAndPassword(email, password).orElse(null);
        return doctor;
    }

    public List<String> getDoctorName(int id){
        Optional<Doctor> doctor = repository.findById(id);
        if(doctor.isPresent()){
            List<String> fullName = new ArrayList<>();
            fullName.add(doctor.get().getLastName());
            fullName.add(doctor.get().getFirstName());
            return fullName;
        }
        else{
            throw new IllegalArgumentException("Doctor not found");
        }
    }
}

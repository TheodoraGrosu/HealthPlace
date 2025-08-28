package com.example.healthplace.SpringServer.MedicalHistoryModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMedicalHistory;
    private int idPatient;
    @Convert(converter = ConvertorList.class)
    @Column(name = "allergies")
    private List<String> allergies;
    @Convert(converter = ConvertorList.class)
    @Column(name = "intolerances")
    private List<String> intolerances;
    @Convert(converter = ConvertorList.class)
    @Column(name = "chronicDiseases")
    private List<String> chronicDiseases;
    @Convert(converter = ConvertorList.class)
    @Column(name = "surgeries")
    private List<String> surgeries;
    @Convert(converter = ConvertorList.class)
    @Column(name = "immunizations")
    private List<String> immunizations;

    public int getIdMedicalHistory() {
        return idMedicalHistory;
    }

    public void setIdMedicalHistory(int idMedicalHistory) {
        this.idMedicalHistory = idMedicalHistory;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getIntolerances() {
        return intolerances;
    }

    public void setIntolerances(List<String> intolerances) {
        this.intolerances = intolerances;
    }

    public List<String> getChronicDiseases() {
        return chronicDiseases;
    }

    public void setChronicDiseases(List<String> chronicDiseases) {
        this.chronicDiseases = chronicDiseases;
    }

    public List<String> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(List<String> surgeries) {
        this.surgeries = surgeries;
    }

    public List<String> getImmunizations() {
        return immunizations;
    }

    public void setImmunizations(List<String> immunizations) {
        this.immunizations = immunizations;
    }
}

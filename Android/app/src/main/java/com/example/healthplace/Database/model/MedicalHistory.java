package com.example.healthplace.Database.model;

import java.util.List;

public class MedicalHistory {
    private int idMedicalHistory;
    private int idPatient;
    private List<String> allergies;
    private List<String> intolerances;
    private List<String> chronicDiseases;
    private List<String> surgeries;
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

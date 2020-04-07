package com.mcdenny.coronavirusapp.model;

public class Form {
    private String id;
    private User user;
    private Symptom symptom;
    private String hospital;

    public Form() {
    }

    public Form(User user, Symptom symptom, String hospital) {
        this.user = user;
        this.symptom = symptom;
        this.hospital = hospital;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Symptom getSymptom() {
        return symptom;
    }

    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
}

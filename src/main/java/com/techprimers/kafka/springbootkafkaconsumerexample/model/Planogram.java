package com.techprimers.kafka.springbootkafkaconsumerexample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Planogram {
    String userName, token, planoID, planoType, planoName;
    String effectiveStartDate, endDate;

    PlanogramDetails planogramDetails =new PlanogramDetails();


    public String getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(String effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPlanoID() {
        return planoID;
    }

    public void setPlanoID(String planoID) {
        this.planoID = planoID;
    }

    public String getPlanoType() {
        return planoType;
    }

    public void setPlanoType(String planoType) {
        this.planoType = planoType;
    }

    public String getPlanoName() {
        return planoName;
    }

    public void setPlanoName(String planoName) {
        this.planoName = planoName;
    }



    public PlanogramDetails getPlanogramDetails() {
        return planogramDetails;
    }

    public void setPlanogramDetails(PlanogramDetails planogramDetails) {
        this.planogramDetails = planogramDetails;
    }

    public Planogram(String userName, String token, String planoID, String planoType, String planoName, String effectiveStartDate, String endDate, PlanogramDetails planogramDetails) {
        this.userName = userName;
        this.token = token;
        this.planoID = planoID;
        this.planoType = planoType;
        this.planoName = planoName;
        this.effectiveStartDate = effectiveStartDate;
        this.endDate = endDate;
        this.planogramDetails = planogramDetails;
    }

    public Planogram(){

    }



}

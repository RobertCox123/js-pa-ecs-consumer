package com.techprimers.kafka.springbootkafkaconsumerexample.model;

public class ECSLoginResponse {

    String token;

Status status;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ECSLoginResponse(String token, Status status) {
        this.token = token;
        this.status = status;
    }

    public ECSLoginResponse(){

    }

}

package com.techprimers.kafka.springbootkafkaconsumerexample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {

    String statusMessage;
    Integer statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Status(String statusMessage, Integer statusCode) {
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
    }

    public Status(){

    }
}

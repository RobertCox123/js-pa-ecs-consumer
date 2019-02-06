package com.techprimers.kafka.springbootkafkaconsumerexample.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techprimers.kafka.springbootkafkaconsumerexample.model.ProductLocation;
import com.techprimers.kafka.springbootkafkaconsumerexample.model.User;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class KafkaConsumer {

    /*
    @KafkaListener(topics = "Product-location-in", group = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
*/
/*
    @KafkaListener(topics = "Product-location-in", group = "group_id")
    public void consumeObject(ProductLocation productLocation)
    {
       String sku= productLocation.getSku();
        System.out.println("**********>>>>Consumed sku: " + sku);
    }
*/
    @KafkaListener(topics = "Product-location-in", group = "group_json",
            containerFactory = "userKafkaListenerFactory")
    public void consumeJson(ProductLocation productLocation) {
        String sku= productLocation.getSku();
        System.out.println("**********>>>>Consumed sku: " + sku);

    }




}

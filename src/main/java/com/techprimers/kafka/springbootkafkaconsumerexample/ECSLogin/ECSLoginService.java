package com.techprimers.kafka.springbootkafkaconsumerexample.ECSLogin;

import com.techprimers.kafka.springbootkafkaconsumerexample.model.User;
import netscape.javascript.JSObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ECSLoginService {

    @Scheduled(fixedRate = 240000)
    public void GetToken(){
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://sainsburysdev.ecsglobalinc.com:8443/ecs/logon.sws";
        String requestJson ="[{\n" +
                "   \"userName\": \"ECS\",\n" +
                "   \"password\": \"ecs\",\n" +
                "   \"apiToken\": \"57e1bc49ff598e7495f8b35739848ad2\"\n" +
                "}]";
        //User user=new User("ECS", "ecs", "57e1bc49ff598e7495f8b35739848ad2");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> entity =new HttpEntity<String>(requestJson, headers);

        System.out.println("Print URL***********" +baseUrl);
        System.out.println("Print entity>>>>>>>>>>" +entity.toString());

        //JSObject jobj=new
        // I have to develop exception handing code.
        Object  result123 = restTemplate.postForObject(baseUrl, entity, String.class);
        System.out.println("Print ECS Response ::::::::::::>>>>" +result123.toString());

    }
}

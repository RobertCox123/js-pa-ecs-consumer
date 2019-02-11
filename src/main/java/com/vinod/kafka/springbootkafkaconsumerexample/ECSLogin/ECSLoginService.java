package com.vinod.kafka.springbootkafkaconsumerexample.ECSLogin;

import com.google.gson.Gson;
import com.vinod.kafka.springbootkafkaconsumerexample.config.DataConfig;
import com.vinod.kafka.springbootkafkaconsumerexample.model.ECSLoginResponse;
import com.vinod.kafka.springbootkafkaconsumerexample.model.User;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;



@Component
public class ECSLoginService {


    ECSLoginResponse ecsLoginResponse;


    @Scheduled(fixedRate = 240000)
    public void GetToken(){
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://sainsburysdev.ecsglobalinc.com:8443/ecs/logon.sws";

        ArrayList<User> listOfUsers = new ArrayList<User>();
        User ecsLoginRequest = new User();

        ecsLoginRequest.setUserName(DataConfig.USERNAME);
        ecsLoginRequest.setPassword(DataConfig.PASSWORD);
        ecsLoginRequest.setApiToken(DataConfig.API_TOKEN);
        listOfUsers.add(ecsLoginRequest);
        String jsonText = new Gson().toJson(listOfUsers);
        System.out.println("this is Vinod output to ECS**********>" + jsonText);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(jsonText, headers);
        try {
            ResponseEntity<ECSLoginResponse[]> response = restTemplate.exchange(DataConfig.LOGON_URL, HttpMethod.POST, entity, ECSLoginResponse[].class);
            System.out.println("Print STATUS CODE >>>>" + response.getStatusCode());
            if (response.getStatusCode() == HttpStatus.OK) {
                ECSLoginResponse[] ecsLoginResponse = response.getBody();
                try {
                    DataConfig.TOKEN = ecsLoginResponse[0].getToken();
                    System.out.println("ECS TOKEN >>>>> " + ecsLoginResponse[0].getToken());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                DataConfig.TOKEN = null;
                System.out.println("inner catch block");
            }
        } catch (Exception e) {
            System.out.println("outer catch block");
            DataConfig.TOKEN = null;
        }











/*



        System.out.println("Print URL***********" +baseUrl);
        System.out.println("Print entity>>>>>>>>>>" +entity.toString());

        String ecsResponse = restTemplate.postForObject(baseUrl, entity, String.class);

        System.out.println("Print ECS Response ::::::::::::>>>>" +ecsResponse);


        try {
            JSONArray prasadArrcsy = new JSONArray(ecsResponse);
            JSONObject jsnobject = prasadArrcsy.getJSONObject(0);
            DataConfig.TOKEN= (String) jsnobject.get("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }



        //DataConfig.TOKEN= ecsLoginResponse.getToken();

        System.out.println("Print ECS TOKEN ::::::::::::>>>>" +DataConfig.TOKEN);

        */

    }
}

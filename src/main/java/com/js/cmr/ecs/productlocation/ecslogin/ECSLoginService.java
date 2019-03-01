package com.js.cmr.ecs.productlocation.ecslogin;

import com.google.gson.Gson;
import com.js.cmr.ecs.productlocation.config.DataConfig;
import com.js.cmr.ecs.productlocation.model.ECSLoginResponse;
import com.js.cmr.ecs.productlocation.model.User;
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
        ArrayList<User> listOfUsers = new ArrayList<User>();

        User ecsLoginRequest = new User();
        ecsLoginRequest.setUserName(DataConfig.USERNAME);
        ecsLoginRequest.setPassword(DataConfig.PASSWORD);
        ecsLoginRequest.setApiToken(DataConfig.API_TOKEN);

        listOfUsers.add(ecsLoginRequest);

        String jsonText = new Gson().toJson(listOfUsers);
        System.out.println("ECS login request message **********>" + jsonText);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(jsonText, headers);
        try {
            ResponseEntity<ECSLoginResponse[]> response = restTemplate.exchange(DataConfig.LOGON_URL, HttpMethod.POST, entity, ECSLoginResponse[].class);
            System.out.println("Print ECS STATUS CODE >>>> : " + response.getStatusCode());
            if (response.getStatusCode() == HttpStatus.OK) {
                ECSLoginResponse[] ecsLoginResponse = response.getBody();
                try {
                    DataConfig.TOKEN = ecsLoginResponse[0].getToken();
                    System.out.println("ECS TOKEN >>>>> : " + ecsLoginResponse[0].getToken());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                DataConfig.TOKEN = null;
                System.out.println("ECS Status code is not OK and TOKEN set to null");
            }
        } catch (Exception e) {
            System.out.println("ECS login service failed ");
            DataConfig.TOKEN = null;
        }
    }
}

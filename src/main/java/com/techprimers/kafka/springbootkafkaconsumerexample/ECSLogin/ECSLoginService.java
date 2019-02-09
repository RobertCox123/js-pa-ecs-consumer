package com.techprimers.kafka.springbootkafkaconsumerexample.ECSLogin;

import com.techprimers.kafka.springbootkafkaconsumerexample.config.DataConfig;
import com.techprimers.kafka.springbootkafkaconsumerexample.model.ECSLoginResponse;
import com.techprimers.kafka.springbootkafkaconsumerexample.model.User;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Component
public class ECSLoginService {


    ECSLoginResponse ecsLoginResponse;


    @Scheduled(fixedRate = 24000)
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

        //headers.setAccept(MediaType.APPLICATION_JSON);



          // String ecsLoginResponse = restTemplate.postForObject("https://sainsburysdev.ecsglobalinc.com:8443/ecs/logon.sws", user, String.class );

           //System.out.println("Print ECS Response ::::::::::::>>>>" + ecsLoginResponse);


        HttpEntity<String> entity =new HttpEntity<String>(requestJson, headers);

        System.out.println("Print URL***********" +baseUrl);
        System.out.println("Print entity>>>>>>>>>>" +entity.toString());


        //ecsLoginResponse = restTemplate.postForObject(baseUrl, entity, ECSLoginResponse.class);

        String ecsResponse = restTemplate.postForObject(baseUrl, entity, String.class);

        System.out.println("Print ECS Response ::::::::::::>>>>" +ecsResponse);


        //JSONObject ecsResponse123 = restTemplate.postForObject(baseUrl, entity, JSONObject.class);
        try {
            JSONArray prasadArrcsy = new JSONArray(ecsResponse);
            JSONObject jsnobject = prasadArrcsy.getJSONObject(0);
            DataConfig.TOKEN= (String) jsnobject.get("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }



        //DataConfig.TOKEN= ecsLoginResponse.getToken();

        System.out.println("Print ECS TOKEN ::::::::::::>>>>" +DataConfig.TOKEN);

    }
}

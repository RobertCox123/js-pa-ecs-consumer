package com.js.cmr.ecs.productlocation.config;

import com.js.cmr.ecs.productlocation.ecslogin.ECSLoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataConfig {

    public static String TOKEN;
    public static String USERNAME;
    public static String PASSWORD;
    public static String API_TOKEN;
    public static String LOGON_URL;
    public static String UPSERT_URL;
    public static String DELETE_URL;

    @Value("${user_name}")
    public void setUsername(String username) {
        USERNAME = username;
    }

    @Value("${password}")
    public void setPassword(String password) {
        PASSWORD = password;
    }

    @Value("${api_token}")
    public void setApiToken(String api_token) {
        API_TOKEN = api_token;
    }

    @Value("${ecs_logon_url}")
    public void setLogonURL(String ecs_logon_url) {
        LOGON_URL = ecs_logon_url;
    }



    @Value("${ecs_planogram_upsert_url}")
    public void setECSPlanogramUpsertUrl(String ecs_planogram_upsert_url) {
        UPSERT_URL = ecs_planogram_upsert_url;
    }

    @Value("${ecs_planogram_delete_url}")
    public void setECSPlanogramDeleteUrl(String ecs_planogram_delete_url) {
        DELETE_URL = ecs_planogram_delete_url;
    }

    @Value("")
    public void setECSToken() {
        new ECSLoginService().GetToken();
    }

}

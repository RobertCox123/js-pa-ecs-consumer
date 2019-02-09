package com.techprimers.kafka.springbootkafkaconsumerexample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

@PropertySource("classpath:application.properties")
public class DataConfig {

    public static String TOKEN;
    public static String USERNAME;
    public static String PASSWORD;
    public static String API_TOKEN;

    @Autowired
    Environment envECS;

   Properties hibernates(){

       Properties properties = new Properties();

       properties.setProperty("UserName",envECS.getProperty(DataConfig.USERNAME));
       return properties;
   }

}

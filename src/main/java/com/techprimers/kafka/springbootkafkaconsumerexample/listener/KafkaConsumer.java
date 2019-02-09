package com.techprimers.kafka.springbootkafkaconsumerexample.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techprimers.kafka.springbootkafkaconsumerexample.config.DataConfig;
import com.techprimers.kafka.springbootkafkaconsumerexample.model.Planogram;
import com.techprimers.kafka.springbootkafkaconsumerexample.model.PlanogramDetails;
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

        Planogram planogram=new Planogram();
        PlanogramDetails planogramDetails =new PlanogramDetails();

        planogram.setUserName("ECS");
        planogram.setToken(DataConfig.TOKEN);
        planogram.setPlanoID(productLocation.getStore_number()+"-"+productLocation.getSub_category());
        planogram.setPlanoType(productLocation.getSelling_location_type());
        planogram.setPlanoName(productLocation.getSub_category());
        planogram.setEffectiveStartDate("2000-01-01 00:00:00.0");
        planogram.setEndDate("9999-12-31 00:00:00.0");
        planogramDetails.setStartDate("2000-01-01 00:00:00.0");
        planogramDetails.setEndDate("9999-12-31 00:00:00.0");
        if(productLocation.getLocation_indicator().equals("1"))
            planogramDetails.setHomeLoc("Y");
        else
            planogramDetails.setHomeLoc("N");


        planogramDetails.setHierCd(null);
        planogramDetails.setHierTypCd(null);
        planogramDetails.setAisle("0");
        planogramDetails.setBay("0");
        planogramDetails.setShelfType("0");
        if(productLocation.getSelling_location_type().equals("0") && productLocation.getLocation_indicator().equals("0")
                && productLocation.getProduct_sequence_within_subcat().equals(null)) {
            planogramDetails.setShelf("1");

            planogramDetails.setShelfSeq("0");
        }
        else {
            planogramDetails.setShelf("0");

            planogramDetails.setShelfSeq(productLocation.getProduct_sequence_within_subcat());

        }

        //test this
        planogramDetails.setFacings(Integer.parseInt(productLocation.getWidth()));
        System.out.println("test Facing value" + Integer.parseInt(productLocation.getWidth()));

        planogramDetails.setDepth(Integer.parseInt(productLocation.getCapacity()));
        planogramDetails.setDefaultStyle("0");
        planogramDetails.setPrdWidth(0F);
        planogramDetails.setPrdHeight(0F);
        planogramDetails.setPrdCd(productLocation.getSku());
        planogramDetails.setPrdTypCd("SKU");
        planogramDetails.setAttr_1(productLocation.getStore_aisle_id());
        planogramDetails.setAttr_2(productLocation.getStore_logical_bay_id());
        planogramDetails.setAttr_3(productLocation.getStore_shelf_id());
        planogramDetails.setAttr_4(productLocation.getStore_item_shelf_sequence());
        planogramDetails.setAttr_5(productLocation.getDefault_pack_size());
        planogramDetails.setAttr_6(productLocation.getStore_plinth_id());
        planogramDetails.setDefaultPrtQty(1);
        planogramDetails.setCheckProduct(false);


        planogram.setPlanogramDetails(planogramDetails);




        String sku= productLocation.getSku();
        System.out.println("**********>>>>Consumed sku: " + sku);
        if (productLocation.getEvent_type().equals("I")) {
            String createPlanogramURI = "https://sainsburysdev.ecsglobalinc.com:8443/ecs/create-planogram.sws";
            List<Planogram> planogramsList = new ArrayList<>();
            String planogramJSONString = planogramsList.toString();

            System.out.println("this is final ECS request messgae *********"+ planogramJSONString);



        }


    }




}

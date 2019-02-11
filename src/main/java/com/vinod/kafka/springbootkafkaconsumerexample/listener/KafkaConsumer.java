package com.vinod.kafka.springbootkafkaconsumerexample.listener;

import com.google.gson.Gson;
import com.vinod.kafka.springbootkafkaconsumerexample.config.DataConfig;
import com.vinod.kafka.springbootkafkaconsumerexample.model.ECSPlanogramResponse;
import com.vinod.kafka.springbootkafkaconsumerexample.model.Planogram;
import com.vinod.kafka.springbootkafkaconsumerexample.model.PlanogramDetails;
import com.vinod.kafka.springbootkafkaconsumerexample.model.ProductLocation;
//import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumer {


    //@KafkaListener(topics = "Product-location-in", group = "group_json", containerFactory = "planKafkaListenerFactory")
    @KafkaListener(topics = "Product-location-in")
    public void consumeJson(ProductLocation productLocation, Acknowledgment acknowledgment) {
        // private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);


        boolean commitOffsets = false;

        //MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("Product-location-in");
        String action = productLocation.getEvent_type();

        System.out.println("ECS Event from RSS ***SKU>>>>>>>>>" + productLocation.getSku());

        commitOffsets = true;

  /*
        if (action.equals("I")||action.equals("D")) {
            HttpStatus flag  = postPlanogramDetailsToECS(planogramsMappingToEcsRequest(productLocation),action.equals("I")?DataConfig.UPSERT_URL:DataConfig.DELETE_URL);
            if(flag.equals(HttpStatus.OK))
            {
                logger.info("ECS REST call success for EventType I OR D");
                commitOffsets = true;
            }
            else
            {
                commitOffsets = false;

                logger.info("ECS Request Failed for EventType I OR D!");
            }
        }else if(action.equals("U")||action.equals("S"))
        {
            if(compareOldAndNewValues(productLocation)==true)
            {
                List<Planogram> planogramList = planogramsMappingToEcsRequest(productLocation);
                planogramList.get(0).getPlanogramDetails().get(0).setShelfSeq(productLocation.getOld_product_sequence_within_subcat());
                HttpStatus flag  = postPlanogramDetailsToECS(planogramList,DataConfig.DELETE_URL);

                if(flag.equals(HttpStatus.OK))
                {
                    logger.info("ECS REST call success for EventType S and delete successful !");
                    HttpStatus flag_1 = postPlanogramDetailsToECS(planogramsMappingToEcsRequest(productLocation),DataConfig.CREATE_URL);
                    if(flag_1.equals(HttpStatus.OK))
                    {
                        logger.info("ECS REST call success for EventType S and Insert successful !");
                        commitOffsets = true;
                    }
                    else
                    {
                        commitOffsets = false;

                        logger.info("ECS REST call fail for EventType S and Insert fail !");
                    }
                }
                else
                {
                    commitOffsets = false;
                    //listenerContainer.stop();
                    logger.info("ECS REST call Fail for EventType S !");
                }
            }
            else
            {
                HttpStatus flag  = postPlanogramDetailsToECS(planogramsMappingToEcsRequest(productLocation),DataConfig.UPSERT_URL);
                if(flag.equals(HttpStatus.OK))
                {
                    commitOffsets = true;
                    logger.info("ECS REST call success for EventType U!");
                }
                else
                {
                    commitOffsets = false;
                    //listenerContainer.stop();
                    logger.info("ECS REST Call fail for EventType U!");
                }
            }
        }else
        {
            commitOffsets = true;
            logger.info("Invalid action received from RSS !");
        }


  */


        if (commitOffsets) {
            System.out.println("No exceptions, committing offsets.");

            /*
            Committing the offset to Kafka.
             */
            acknowledgment.acknowledge();
        }
    }

    public List<Planogram> planogramsMappingToEcsRequest(ProductLocation productLocation) {
        Planogram planogram = new Planogram();
        PlanogramDetails planogramDetails = new PlanogramDetails();

        planogram.setUserName(DataConfig.USERNAME);
        planogram.setToken(DataConfig.TOKEN);
        planogram.setPlanoID(productLocation.getStore_number() + "-" + productLocation.getSub_category());
        planogram.setPlanoType(productLocation.getSelling_location_type());
        planogram.setPlanoName(productLocation.getSub_category());
        planogram.setEffectiveStartDate("2000-01-01 00:00:00.0");
        planogram.setEndDate("9999-12-31 00:00:00.0");
        planogramDetails.setStartDate("2000-01-01 00:00:00.0");
        planogramDetails.setEndDate("9999-12-31 00:00:00.0");

        if (productLocation.getLocation_indicator().equals("1"))
            planogramDetails.setHomeLoc("Y");
        else
            planogramDetails.setHomeLoc("N");

        planogramDetails.setHierCd(null);
        planogramDetails.setHierTypCd(null);
        planogramDetails.setAisle("0");
        planogramDetails.setBay("0");
        planogramDetails.setShelfType("0");
        if (productLocation.getSelling_location_type().equals("0") && productLocation.getLocation_indicator().equals("0")
                && productLocation.getProduct_sequence_within_subcat().equals(null)) {
            planogramDetails.setShelf("1");
            planogramDetails.setShelfSeq("0");
        } else {
            planogramDetails.setShelf("0");
            planogramDetails.setShelfSeq(productLocation.getProduct_sequence_within_subcat());
        }

        //test this vinod
        planogramDetails.setFacings(Integer.parseInt(productLocation.getWidth()));
        System.out.println("test Facing value" + Integer.parseInt(productLocation.getWidth()));

        planogramDetails.setDepth(Integer.parseInt(productLocation.getCapacity()));
        System.out.println("test Depth value" + Integer.parseInt(productLocation.getCapacity()));

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
        List<PlanogramDetails> planogramDetailsList = new ArrayList<>();
        planogramDetailsList.add(planogramDetails);
        planogram.setPlanogramDetails(planogramDetailsList);

        List<Planogram> planogramList = new ArrayList<>();
        planogramList.add(planogram);
        return planogramList;
    }

    public boolean compareOldAndNewValues(ProductLocation productLocation) {
        return !productLocation.getOld_product_sequence_within_subcat().equals(productLocation.getProduct_sequence_within_subcat())
                && productLocation.getOld_product_sequence_within_subcat() != null
                && !productLocation.getOld_product_sequence_within_subcat().equals("");
    }

    public HttpStatus postPlanogramDetailsToECS(List<Planogram> planogramList, String url) {
        RestTemplate restPlanogramTemplate = null;
        ResponseEntity<ECSPlanogramResponse[]> response = null;
        try {
            restPlanogramTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(new Gson().toJson(planogramList), headers);
            System.out.println("ECS Request Message : " + entity.toString());

            response = restPlanogramTemplate.exchange(url, HttpMethod.POST, entity, ECSPlanogramResponse[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("ECS STATUS CODE : >" + response.getStatusCode());
                ECSPlanogramResponse[] ecsPlanogramResponse = response.getBody();


                try {
                    System.out.println("ECS Response : " + new Gson().toJson(ecsPlanogramResponse));
                    //  logger.info("ECS Response...line number 211:"+ecsPlanogramResponse[0].getStatus());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("ECS Exception in: " + new Gson().toJson(ecsPlanogramResponse));
                }
            } else {
                System.out.println("Inner catch block");
                if (response != null) {
                    return response.getStatusCode();
                } else {
                    System.out.println("status code Internal server error:::::::line   226");
                    return HttpStatus.INTERNAL_SERVER_ERROR;

                }
            }
        } catch (Exception e) {
            System.out.println("outer catch block error message line number 233" + e.toString());
            if (response != null) {
                return response.getStatusCode();
            } else {
                System.out.println("status code Internal server error:::::::line   240");
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        return response.getStatusCode();
    }
}

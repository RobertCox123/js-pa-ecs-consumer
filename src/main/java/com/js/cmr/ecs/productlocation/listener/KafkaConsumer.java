package com.js.cmr.ecs.productlocation.listener;

import com.google.gson.Gson;
import com.js.cmr.ecs.productlocation.config.DataConfig;
import com.js.cmr.ecs.productlocation.ecslogin.ECSLoginService;
import com.js.cmr.ecs.productlocation.model.ECSPlanogramResponse;
import com.js.cmr.ecs.productlocation.model.Planogram;
import com.js.cmr.ecs.productlocation.model.PlanogramDetails;
import com.js.cmr.ecs.productlocation.model.ProductLocation;
//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service


public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    boolean fail = true;
    int threadSleep = 1000;


    @KafkaListener(topics = "Product-location-out")
    public void consumeJson(ProductLocation productLocation, Acknowledgment acknowledgment) throws Exception {


        boolean commitOffsets = false;

        String action = productLocation.getEvent_type();

        LOGGER.info("RSS unique Transaction ID : " + productLocation.getItem_loc_chg_txn_id());
        LOGGER.info("Event Type : " + action);



        if (action.equals("I") || action.equals("D")) {
            HttpStatus flag = postPlanogramDetailsToECS(planogramsMappingToEcsRequest(productLocation), action.equals("I") ? DataConfig.UPSERT_URL : DataConfig.DELETE_URL);
            if (flag.equals(HttpStatus.OK)) {
                threadSleep = 15000;
                System.out.println("ECS REST call success for EventType I OR D");
                commitOffsets = true;
            } else {
                commitOffsets = false;

                System.out.println("ECS Request Failed for EventType I OR D!");
                Thread.sleep(threadSleep);
                threadSleep = threadSleep + 12000;
                throw new RuntimeException("ECS Server is down, please check ECS server availability !");
            }
        } else if (action.equals("U") || action.equals("S")) {
            LOGGER.info("If eventType U *********************$$$$$$$$$$!");
            if (compareOldAndNewValues(productLocation) == true) {
                LOGGER.info("If eventType U *********************!");
                List<Planogram> planogramList = planogramsMappingToEcsRequest(productLocation);
                planogramList.get(0).getPlanogramDetails().get(0).setShelfSeq(productLocation.getOld_product_sequence_within_subcat());
                HttpStatus flag = postPlanogramDetailsToECS(planogramList, DataConfig.DELETE_URL);

                if (flag.equals(HttpStatus.OK)) {
                    threadSleep = 15000;
                    LOGGER.info("ECS REST call success for EventType S and delete successful !");

                    HttpStatus flag_1 = postPlanogramDetailsToECS(planogramsMappingToEcsRequest(productLocation), DataConfig.UPSERT_URL);
                    if (flag_1.equals(HttpStatus.OK)) {
                        LOGGER.info("ECS REST call success for EventType S and Insert successful !");

                        commitOffsets = true;
                    } else {
                        commitOffsets = false;
                        LOGGER.info("ECS Server is down and thread sleep for !" + threadSleep);
                        LOGGER.info("ECS REST call fail for EventType S and Insert fail !");

                        Thread.sleep(threadSleep);
                        threadSleep = threadSleep + 12000;
                        throw new RuntimeException("ECS Server is down, please check ECS server availability !");
                    }
                } else {
                    commitOffsets = false;
                    //listenerContainer.stop();
                    LOGGER.info("ECS Server is down and thread sleep for !" + threadSleep);
                    Thread.sleep(threadSleep);
                    threadSleep = threadSleep + 12000;
                    throw new RuntimeException("ECS Server is down, please check ECS server availability !");

                }
            } else {
                LOGGER.info("If eventType U but Upsert $$$$$$$$$$$$$*********************!");
                HttpStatus flag = postPlanogramDetailsToECS(planogramsMappingToEcsRequest(productLocation), DataConfig.UPSERT_URL);
                if (flag.equals(HttpStatus.OK)) {
                    threadSleep = 15000;
                    commitOffsets = true;
                    LOGGER.info("ECS REST call success for EventType U!");


                } else {
                    commitOffsets = false;
                    LOGGER.info("ECS Server is down and thread sleep for !" + threadSleep);
                    LOGGER.info("ECS REST Call fail for EventType U!");
                    //listenerContainer.stop();

                    Thread.sleep(threadSleep);
                    threadSleep = threadSleep + 12000;
                    throw new RuntimeException("ECS Server is down, please check ECS server availability !");
                }
            }
        } else {
            commitOffsets = true;
            LOGGER.info("Invalid action received from RSS discard the message from Kafka TOPIC!");

        }


        if (commitOffsets) {
            /*
            Committing the offset to Kafka.
             */
            acknowledgment.acknowledge();
        }

    }

    public List<Planogram> planogramsMappingToEcsRequest(ProductLocation productLocation) {
        Planogram planogram = new Planogram();
        PlanogramDetails planogramDetails = new PlanogramDetails();
        List<Planogram> planogramList = new ArrayList<>();

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
                && productLocation.getProduct_sequence_within_subcat() == null) {
            planogramDetails.setShelf("1");
            planogramDetails.setShelfSeq("0");
        } else {
            planogramDetails.setShelf("0");
            planogramDetails.setShelfSeq(productLocation.getProduct_sequence_within_subcat());
        }

        planogramDetails.setFacings(Math.round((Float.parseFloat(productLocation.getWidth()))));
        planogramDetails.setDepth(Math.round((Float.parseFloat(productLocation.getCapacity()))));
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

        planogramList.add(planogram);
        return planogramList;
    }

    public boolean compareOldAndNewValues(ProductLocation productLocation) {

        //Check the business logic and inlcude "carTrigger" logic.

        if (productLocation.getOld_product_sequence_within_subcat() != (productLocation.getProduct_sequence_within_subcat()) && (productLocation.getOld_product_sequence_within_subcat() != null)) {
            LOGGER.info("inside TRUE <<<<<<<<<<<<<*********************!");
            return true;
        } else {
            LOGGER.info("inside FALSE <<<<<<<<<<<<<*********************!");
            return false;
        }
    }

    public HttpStatus postPlanogramDetailsToECS(List<Planogram> planogramList, String url) {
        RestTemplate restPlanogramTemplate = null;
        ResponseEntity<ECSPlanogramResponse[]> response = null;
        try {
            restPlanogramTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(new Gson().toJson(planogramList), headers);
            LOGGER.info("ECS Request Message : " + entity.toString());


            response = restPlanogramTemplate.exchange(url, HttpMethod.POST, entity, ECSPlanogramResponse[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                LOGGER.info("ECS STATUS CODE : >" + response.getStatusCode());

                ECSPlanogramResponse[] ecsPlanogramResponse = response.getBody();

                switch (ecsPlanogramResponse[0].getStatus().getStatusCode()) {
                    case 0:
                        LOGGER.info("Message successfully sent to ECS");
                        System.out.println("Sucessfully sent to ECS");
                        break;
                    case 2000:
                        LOGGER.info("DB_UPDATE_FAILED at ECS" + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        System.out.println("DB_UPDATE_FAILED at ECS" + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        return HttpStatus.BAD_REQUEST;
                    case 3004:
                        LOGGER.info("INVALID_TOKEN sent to ECS : " + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        System.out.println("INVALID_TOKEN" + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        new ECSLoginService().GetToken();
                        return HttpStatus.BAD_REQUEST;
                    case 3008:
                        LOGGER.info("PLANOGRAM_ID_NOT_FOUND : " + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        System.out.println("PLANOGRAM_ID_NOT_FOUND" + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        break;
                    case 3010:
                        LOGGER.info("PLANOGRAM_DETAILS_NOT_FOUND : " + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        System.out.println("PLANOGRAM_DETAILS_NOT_FOUND" + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        break;
                    case 3015:
                        System.out.println("PLANOGRAM_ALREADY_DELETED" + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        LOGGER.info("PLANOGRAM_ALREADY_DELETED :" + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        break;
                    case 3017:
                        LOGGER.info("PLANOGRAM_ALREADY_EXIST : " + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        System.out.println("PLANOGRAM_ALREADY_EXIST" + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        break;
                    case 4001:
                        LOGGER.info("DATA_VALIDATION_FAILED : " + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        System.out.println("DATA_VALIDATION_FAILED" + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        break;
                    case 4004:
                        LOGGER.info("DVF_INVALID_PRODUCT_CODE : " + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        System.out.println("DVF_INVALID_PRODUCT_CODE" + ecsPlanogramResponse[0].getStatus().getStatusMessage());
                        break;
                }


            } else {

                System.out.println("Inner catch block");
                LOGGER.info("Inner catch block");
                if (response != null) {
                    return response.getStatusCode();
                } else {
                    LOGGER.info("status code Internal server error:::::::line   260");
                    return HttpStatus.INTERNAL_SERVER_ERROR;

                }
            }
        } catch (Exception e) {
            LOGGER.info("outer catch block error message line number 265" + e.toString());
            if (response != null) {
                return response.getStatusCode();
            } else {
                LOGGER.info("status code Internal server error:::::::line   270");
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        return response.getStatusCode();
    }
}


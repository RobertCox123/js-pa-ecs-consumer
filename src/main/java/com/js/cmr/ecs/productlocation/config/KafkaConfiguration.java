package com.js.cmr.ecs.productlocation.config;

import com.js.cmr.ecs.productlocation.model.ProductLocation;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;


import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Autowired
    private KafkaProperties properties;


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProductLocation> kafkaListenerContainerFactory(
            @Qualifier("retryTemplate") RetryTemplate retryTemplate) {

        ConcurrentKafkaListenerContainerFactory<String, ProductLocation> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        factory.setStatefulRetry(true);

        factory.setConcurrency(10);

        ContainerProperties containerProperties = factory.getContainerProperties();


        factory.setRetryTemplate(retryTemplate());

        containerProperties.setAckOnError(false);
        containerProperties.setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
        containerProperties.setSyncCommits(true);
        containerProperties.setErrorHandler(new SeekToCurrentErrorHandler());

        return factory;
    }


    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,properties.getBootstrapServers());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", ProductLocation.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "planogram-consumer-group-v1");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");
        return props;
    }

    @Bean
    public ConsumerFactory<String, ProductLocation> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new JsonDeserializer<>(ProductLocation.class));
    }





    @Bean
    public ErrorHandler errorHandler() {
        return new SeekToCurrentErrorHandler();
    }


    @Bean
    public RetryPolicy retryPolicy() {
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(10);
        return simpleRetryPolicy;
    }

    @Bean
    public FixedBackOffPolicy backOffPolicy() {
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(1000);
        return backOffPolicy;
    }

    @Bean(name = "retryTemplate")
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        System.out.println("This is message from retryTemplate @@@@@@@@");
        retryTemplate.setRetryPolicy(retryPolicy());
        retryTemplate.setBackOffPolicy(backOffPolicy());
        return retryTemplate;
    }


}

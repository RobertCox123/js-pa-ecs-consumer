package com.vinod.kafka.springbootkafkaconsumerexample.config;

import com.vinod.kafka.springbootkafkaconsumerexample.model.ProductLocation;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class KafkaConfiguration {


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProductLocation> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProductLocation> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(1);
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL);
        factory.getContainerProperties().setSyncCommits(true);
        //factory.getContainerProperties().setErrorHandler(new SeekToCurrentErrorHandler());
        return factory;
    }

    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", ProductLocation.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "customer-consumer-group-v2");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");
        // props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return props;
    }


    @Bean
    public ConsumerFactory<String, ProductLocation> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new JsonDeserializer<>(ProductLocation.class));
    }


}

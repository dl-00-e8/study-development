package com.springboot.pubsub.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.pubsub.v1.PubsubMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PubSubClient {

    private final PubSubTemplate pubSubTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.cloud.gcp.pubsub.topic}")
    private String topicName;

    @Value("${spring.cloud.gcp.pubsub.authentication-key}")
    private String authenticationKey;

    public void publishMessage(MessageContent messageContent) {
        try {
            // Convert to Json
            String message = objectMapper.writeValueAsString(messageContent);

            // Generate PubsubMessage Object with attributes
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                    .setData(com.google.protobuf.ByteString.copyFromUtf8(message))
                    .putAttributes("authentication", authenticationKey)
                    .build();

            // Publish message
            pubSubTemplate.publish(topicName, pubsubMessage);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new RuntimeException(jsonProcessingException.getMessage());
        }
    }
}

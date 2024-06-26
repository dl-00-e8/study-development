package com.springboot.pubsub.pubsub;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class PubSubClient {

    @Value("${spring.cloud.gcp.project-id}")
    private static String projectId;

    @Value("${spring.cloud.gcp.pubsub.topic-id}")
    private static String topicName;

    private final PubSubTemplate pubSubTemplate;

    public void publishMessage(String message) {
        CompletableFuture<String> future = pubSubTemplate.publish(topicName, message);

        future.thenAccept(messageId ->
                System.out.println("Message published successfully with ID: " + messageId)
        ).exceptionally(ex -> {
            System.out.println("Error publishing message: " + ex.getMessage());
            return null;
        });
    }
}

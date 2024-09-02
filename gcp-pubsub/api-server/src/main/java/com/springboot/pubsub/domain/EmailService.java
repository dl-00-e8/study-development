package com.springboot.pubsub.domain;

import com.springboot.pubsub.pubsub.MessageContent;
import com.springboot.pubsub.pubsub.PubSubClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;
    private final PubSubClient pubSubClient;

    public void sendEmail(EmailReq emailReq) {
        Email email = emailReq.from();
        Email savedEmail = emailRepository.save(email);
        MessageContent messageContent = new MessageContent(
                savedEmail.getId(),
                savedEmail.getTitle(),
                savedEmail.getBody(),
                savedEmail.getReceiver()
        );
        pubSubClient.publishMessage(messageContent);
    }
}

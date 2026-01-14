package com.ead.course.publishers;

import com.ead.course.dtos.NotificationDTO;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class NotificationPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${ead.broker.exchange.notificationCommandExchange}")
    private String notificationCommandExchange;

    @Value(value = "${ead.broker.key.notificationCommandKey}")
    private String notificationCommandKey;

    public void publishNotificationCommand(NotificationDTO notificationDTO){
        rabbitTemplate.convertAndSend(notificationCommandExchange, notificationCommandKey, notificationDTO);
    }
}

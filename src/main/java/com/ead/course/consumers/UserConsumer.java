package com.ead.course.consumers;

import com.ead.course.dtos.UserDTO;
import com.ead.course.enums.ActionType;
import com.ead.course.services.UserService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserConsumer {

    @Autowired
    private UserService service;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.userEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.userEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")
    ))
    public void listenUserEvent(@Payload UserDTO dto){

        UUID id = dto.getId();
        switch (ActionType.valueOf(dto.getActionType())){
            case CREATE:
                service.insert(dto);
                break;
            case UPDATE:
                service.update(id, dto);
                break;
            case DELETE:
                service.deleteById(dto.getId());
                break;
        }
    }
}

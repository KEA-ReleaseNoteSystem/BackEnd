package kakao99.backend.notification.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DynamicQueueListener {

    @RabbitListener(queues = "#{messageService.getTestUserIdQueue()}")
    public void testConsumer(String text) {

        System.out.println("Test Received message: " + text);
    }
}
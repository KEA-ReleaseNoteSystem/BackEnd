package kakao99.backend.notification.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewMemberMQConfig {
    // RabbitMQ exchange 이름
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 사용자 가입 이벤트를 받는 리스너
    @RabbitListener(queues = "user_signup_queue")
    public void onUserSignup(String userId) {
        // 새로운 사용자의 큐 이름 생성
        String queueName = "user." + userId;
        System.out.println("user."+userId+" 큐 생성");

        // 큐 생성
        Queue userQueue = new Queue(queueName, false);
        rabbitTemplate.execute(channel -> {
            channel.queueDeclare(queueName, true, false, false, null);
            return null;
        });

        // 큐와 Exchange 바인딩
        Binding binding = BindingBuilder.bind(userQueue)
                .to(new DirectExchange(exchangeName))
//                .with("user." + userId); // Binding Key를 "user.{userId}"로 지정
                    .with("user."+userId); // Binding Key를 "user.{userId}"로 지정
        rabbitTemplate.execute(channel -> {
            channel.queueBind(binding.getDestination(), binding.getExchange(), binding.getRoutingKey());
            return null;
        });
    }

    // RabbitAdmin 빈 생성
    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(rabbitTemplate);
    }

}


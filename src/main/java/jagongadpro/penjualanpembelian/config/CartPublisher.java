package jagongadpro.penjualanpembelian.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendViewCartRequest(String email) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.REQUEST_ROUTING_KEY, email);
    }

    public String getCartResponse() {
        Object response = rabbitTemplate.receiveAndConvert(RabbitMQConfig.RESPONSE_QUEUE);
        return response != null ? response.toString() : "No response received";
    }
}


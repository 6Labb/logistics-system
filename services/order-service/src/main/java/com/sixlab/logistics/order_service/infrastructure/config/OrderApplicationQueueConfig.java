package com.sixlab.logistics.order_service.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderApplicationQueueConfig {

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Binding bindingOrderQueue(TopicExchange orderExchange, Queue orderQueue) {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("order.#");
    }
    @Bean
    public Queue orderInfoQueue() {
        return new Queue("orderInfo-queue", true);
    }

    @Value("${message.exchange}")
    private String exchange;

    @Value("${message.queue.order}")
    private String queueOrder;

    @Bean
    public TopicExchange orderExchange() {return new TopicExchange(exchange);}
    @Bean
    public Queue orderQueue() {return new Queue(queueOrder);}

}

package com.mshop.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class AmqpConfig {
    @Bean
    public FanoutExchange GetFanoutExchange() {
        return new FanoutExchange("orderPayExchange");
    }

    @Bean
    public Queue pointQueue() {
        return new Queue("pointQueue");
    }

    @Bean
    public Queue wuLiuQueue() {
        return new Queue("wuLiuQueue");
    }

    @Bean
    public Binding pointBinding() {
        return new Binding("pointQueue", Binding.DestinationType.QUEUE, "orderPayExchange", "", null);
    }

    @Bean
    public Binding wuLiuBinding() {
        return new Binding("wuLiuQueue", Binding.DestinationType.QUEUE, "orderPayExchange", "", null);
    }

    // 死信队列
    @Bean
    public Queue orderQueue() {
        HashMap<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "orderDeadLetterExchange");
        args.put("x-dead-letter-routing-key", "orderDeadLetterKey");
        return new Queue("orderQueue", true, false, false, args);
    }

    @Bean
    public FanoutExchange orderExchange() {
        return new FanoutExchange("orderExchange");
    }

    @Bean
    public Binding orderBinding() {
        return new Binding("orderQueue", Binding.DestinationType.QUEUE, "orderExchange", "", null);
    }


    @Bean
    public Queue orderDeadLetterQueue() {
        return new Queue("orderDeadLetterQueue");
    }

    @Bean
    public DirectExchange orderDeadLetterExchange() {
        return new DirectExchange("orderDeadLetterExchange");
    }

    @Bean
    public Binding orderDeadLetterBinding() {
        return new Binding("orderDeadLetterQueue", Binding.DestinationType.QUEUE, "orderDeadLetterExchange", "orderDeadLetterKey", null);
    }
}

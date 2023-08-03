package com.mshop.listener;

import com.alibaba.fastjson.JSONObject;
import com.mshop.commons.Constants;
import com.mshop.domain.Order;
import com.mshop.service.OrderService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SecKillListener {
    @Resource
    private OrderService orderService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(
            bindings = {
                    @QueueBinding(
                            value = @Queue("secKillQueue"),
                            exchange = @Exchange(name = "secKillExchange", type = "fanout")
                    )
            }
    )
    public void secKillMessageListener(String message) {
        Order order = JSONObject.parseObject(message, Order.class);
        int row = orderService.addOrder(order);
        if (0 == row) {
            stringRedisTemplate.opsForZSet().remove(Constants.ORDERS, message);
        }
    }
}

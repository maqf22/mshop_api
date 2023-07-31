package com.mshop.listener;

import com.alibaba.fastjson.JSONObject;
import com.mshop.domain.Order;
import com.mshop.domain.OrderInfo;
import com.mshop.service.OrderService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

@Component
public class RabbitMQListener {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private OrderService orderService;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("orderStatus"),
                    exchange = @Exchange(name = "orderStatusExchange", type = "fanout")
            )
    })
    public void deleteOrderFromRedis(String message) {
        // 将订单备份数据从Redis中册除，这样定时任务就不会再继续发送消息，表示订单完成
        // 我们利用监听器从MQ中取出的数据积分或物流服务写入到MQ中的订单备份数据的JSON
        stringRedisTemplate.opsForZSet().remove("order", message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    key = {"orderDeadLetterKey"},
                    value = @Queue("orderDeadLetterQueue"),
                    exchange = @Exchange(name = "orderDeadLetterExchange", type = "direct")
            )
    })
    public void orderDeadLetterListener(String message) {
        HashMap map = JSONObject.parseObject(message, HashMap.class);
        JSONObject orderJson = (JSONObject) map.get("order");
        JSONObject orderInfoJson = (JSONObject) map.get("orderInfo");
        Order order = orderJson.toJavaObject(Order.class);
        OrderInfo orderInfo = orderInfoJson.toJavaObject(OrderInfo.class);
        orderService.cancelOrder(order, orderInfo);
    }
}

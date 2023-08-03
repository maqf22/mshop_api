package com.mshop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mshop.commons.Constants;
import com.mshop.domain.Order;
import com.mshop.mapper.OrderMapper;
import com.mshop.service.OrderService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public int addOrder(Order order) {
        try {
            order.setBuyNum(1);
            order.setStatus(1);
            order.setCreateTime(new Date());
            order.setOrderMoney(order.getBuyPrice().multiply(new BigDecimal(order.getBuyNum())));
            System.out.println(order);
            orderMapper.insert(order);
            // 使用统一key前缀+商品id+用户id 作为key,使用订单对象的Json作为value，并指定超时间将订单结果存入Redis中，用于通知前端
            stringRedisTemplate.opsForValue().set(
                    Constants.ORDERS_RESULT + order.getGoodsId() + ":" + order.getUid(),
                    JSONObject.toJSONString(order),
                    Duration.ofSeconds(5 * 60)
            );
        } catch (DuplicateKeyException e) {
            return 0;
        }
        return 0;
    }

    @Override
    public Order getOrderResult(Long goodsId, Long userId) {
        String orderJson = stringRedisTemplate.opsForValue().get(Constants.ORDERS_RESULT + goodsId + ":" + userId);
        System.out.println(orderJson);
        return JSONObject.parseObject(orderJson, Order.class);
    }
}

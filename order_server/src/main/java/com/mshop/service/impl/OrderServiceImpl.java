package com.mshop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mshop.commons.Code;
import com.mshop.commons.JsonResult;
import com.mshop.domain.Order;
import com.mshop.domain.OrderInfo;
import com.mshop.domain.VConfirmOrderInfo;
import com.mshop.mapper.OrderInfoMapper;
import com.mshop.mapper.OrderMapper;
import com.mshop.mapper.VConfirmOrderInfoMapper;
import com.mshop.service.OrderService;
import com.mshop.service.remote.RemoteGoodsService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    OrderInfoMapper orderInfoMapper;
    @Resource
    private RemoteGoodsService remoteGoodsService;
    @Resource
    private VConfirmOrderInfoMapper vConfirmOrderInfoMapper;
    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    @Transactional
    @GlobalTransactional // 开启Seata全局事务（分布式事务）
    public Object addOrder(Long goodsId, Integer buyNum, Long userId) {
        JsonResult<BigDecimal> res = remoteGoodsService.decrGoodsStore(goodsId, buyNum);
        System.out.println(res.getCode());
        System.out.println(Code.NO_STORE.getCode());
        if (res.getCode().equals(Code.NO_STORE.getCode())) {
            return "0";
        }
        BigDecimal goodsPrice = res.getResult();
        System.out.println(goodsPrice);
        // 减少库存成功
        Order order = new Order();
        order.setOrderMoney(goodsPrice.multiply(new BigDecimal(buyNum)));
        order.setStatus("0");
        order.setUserId(userId);
        order.setCreateTime(System.currentTimeMillis());
        order.setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""));
        int row = orderMapper.insert(order);
        if (0 == row) {
            return "-1";
        }
        // 订单添加成功
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setPrice(goodsPrice);
        orderInfo.setAmount(buyNum);
        orderInfo.setGoodsId(goodsId);
        orderInfo.setOrderId(order.getId());
        int row2 = orderInfoMapper.insert(orderInfo);
        if (0 == row2) {
            return "-2";
        }
        HashMap messageMap = new HashMap();
        messageMap.put("order", order);
        messageMap.put("orderInfo", orderInfo);
        String messageJson = JSONObject.toJSONString(messageMap);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("20000");
        Message message = new Message(messageJson.getBytes(), messageProperties);
        amqpTemplate.send("orderExchange","", message);
        return order;
    }

    @Override
    public List<VConfirmOrderInfo> getOrderInfoByOrderId(Long orderId) {
        QueryWrapper<VConfirmOrderInfo> vConfirmOrderInfoQW = new QueryWrapper<>();
        vConfirmOrderInfoQW.eq("order_id", orderId);
        return vConfirmOrderInfoMapper.selectList(vConfirmOrderInfoQW);
    }

    @Override
    public void orderPay(Order order) {
        orderMapper.updateById(order);
        // 通知物流系统和积分系统
        amqpTemplate.convertAndSend("orderPayExchange", "", JSONObject.toJSONString(order));
    }

    @Override
    public void checkOrderPay(String tradeStatus, Order order) {
        if ("trade_status".equals(tradeStatus)) {
            orderMapper.updateById(order);
            // 通知物流系统和积分系统
            amqpTemplate.convertAndSend("orderPayExchange", "", JSONObject.toJSONString(order));
        }
    }

    @Override
    @Transactional
    @GlobalTransactional
    public void cancelOrder(Order order, OrderInfo orderInfo) {
        if (order.getStatus().equals("0")) {
            orderInfoMapper.deleteById(orderInfo.getId());
            orderMapper.deleteById(order.getId());
            remoteGoodsService.incrGoodsStore(orderInfo.getGoodsId(), orderInfo.getAmount());
        }
    }
}

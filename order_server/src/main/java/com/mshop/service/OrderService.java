package com.mshop.service;

import com.mshop.domain.Order;
import com.mshop.domain.OrderInfo;
import com.mshop.domain.VConfirmOrderInfo;

import java.util.List;

public interface OrderService {

    Object addOrder(Long goodsId, Integer buyNum, Long userId);

    List<VConfirmOrderInfo> getOrderInfoByOrderId(Long orderId);

    void orderPay(Order order);

    void checkOrderPay(String tradeStatus, Order order);

    void cancelOrder(Order order, OrderInfo orderInfo);
}

package com.mshop.service;

import com.mshop.domain.Order;

public interface OrderService {
    int addOrder(Order order);

    Order getOrderResult(Long goodsId, Long userId);
}

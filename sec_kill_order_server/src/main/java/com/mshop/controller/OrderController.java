package com.mshop.controller;

import com.mshop.commons.Code;
import com.mshop.commons.JsonResult;
import com.mshop.domain.Order;
import com.mshop.remote.RemoteUserService;
import com.mshop.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private RemoteUserService remoteUserService;

    @RequestMapping("/getOrderResult")
    public JsonResult<Object> getOrderResult(String token, Long goodsId) {
        JsonResult<Long> userIdJson= remoteUserService.getUserId(token);
        if (Code.NOT_LOGIN.getCode().equals(userIdJson.getCode())) {
            return new JsonResult<>(Code.NOT_LOGIN, null);
        }
        Order order = orderService.getOrderResult(goodsId, userIdJson.getResult());
        return new JsonResult<>(Code.OK, order);
    }
}

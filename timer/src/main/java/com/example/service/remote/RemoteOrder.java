package com.example.service.remote;

import com.mshop.commons.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "OrderServer")
public interface RemoteOrder {
    @RequestMapping("/checkOrderPay")
    JsonResult checkOrderPay(@RequestParam String orderJson);
}

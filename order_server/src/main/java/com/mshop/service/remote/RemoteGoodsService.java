package com.mshop.service.remote;

import com.mshop.commons.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("GoodsServer")
public interface RemoteGoodsService {
    @GetMapping("/decrGoodsStore")
    JsonResult<BigDecimal> decrGoodsStore(@RequestParam Long goodsId, @RequestParam Integer buyNum);

    @RequestMapping("/incrGoodsStore")
    void incrGoodsStore(@RequestParam Long goodsId, @RequestParam Integer buyNum);
}

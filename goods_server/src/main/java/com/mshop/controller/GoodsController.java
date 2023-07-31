package com.mshop.controller;

import com.mshop.commons.Code;
import com.mshop.commons.JsonResult;
import com.mshop.service.GoodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class GoodsController {
    @Resource
    private GoodsService goodsServer;

    @GetMapping("/decrGoodsStore")
    public JsonResult<BigDecimal> decrGoodsStore(Long goodsId, Integer buyNum) {
        BigDecimal price = goodsServer.decrGoodsStore(goodsId, buyNum);
        if (null == price) {
            return new JsonResult<>(Code.NO_STORE, null);
        }
        return new JsonResult<>(Code.OK, price);
    }
    @GetMapping("/incrGoodsStore")
    public void incrGoodsStore(Long goodsId, Integer buyNum) {
        goodsServer.incrGoodsStore(goodsId, buyNum);
    }

}

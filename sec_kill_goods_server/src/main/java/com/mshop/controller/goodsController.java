package com.mshop.controller;

import com.mshop.commons.Code;
import com.mshop.commons.JsonResult;
import com.mshop.domain.Goods;
import com.mshop.service.GoodsService;
import com.mshop.service.remote.RemoteUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class goodsController {
    @Resource
    private GoodsService goodsService;
    @Resource
    private RemoteUserService remoteUserService;

    @RequestMapping("/getGoodsList")
    public JsonResult<List<Goods>> getGoodsList() {
        List<Goods> goodsList = goodsService.getGoodsList();
        return new JsonResult<>(Code.OK, goodsList);
    }

    @RequestMapping("/secKill")
    public JsonResult<Object> secKill(String token, Long goodsId) {
        JsonResult<Long> userIdJson = remoteUserService.getUserId(token);
        if (null == userIdJson.getResult()) {
            return new JsonResult<>(Code.NOT_LOGIN, null);
        }
        int row = goodsService.secKill(userIdJson.getResult(), goodsId);
        // 0-下单成功 1-活动还没开始 2-活动已结束 3-库存不足 4-用户重复下单
        switch (row) {
            case 1:
                return new JsonResult<>(Code.ERROR, "活动还没开始", null);
            case 2:
                return new JsonResult<>(Code.ERROR, "活动已结束了", null);
            case 3:
                return new JsonResult<>(Code.ERROR, "商品已卖光", null);
            case 4:
                return new JsonResult<>(Code.ERROR, "不能重复下单", null);
        }
        return new JsonResult<>(Code.OK, row);
    }
}

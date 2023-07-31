package com.mshop.controller;

import com.mshop.commons.Code;
import com.mshop.commons.JsonResult;
import com.mshop.domain.Goods;
import com.mshop.service.GoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class goodsController {
    @Resource
    private GoodsService goodsService;

    @RequestMapping("/getGoodsList")
    public JsonResult<List<Goods>> getGoodsList() {
        List<Goods> goodsList = goodsService.getGoodsList();
        return new JsonResult<>(Code.OK, goodsList);
    }
}

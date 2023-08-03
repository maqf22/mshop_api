package com.mshop.service;

import com.mshop.commons.JsonResult;
import com.mshop.domain.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> getGoodsList();

    int secKill(Long userId, Long goodsId);
}

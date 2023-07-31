package com.mshop.service;

import java.math.BigDecimal;

public interface GoodsService {
    BigDecimal decrGoodsStore(Long goodsId, Integer buyNum);

    void incrGoodsStore(Long goodsId, Integer buyNum);
}

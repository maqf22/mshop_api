package com.mshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mshop.domain.GoodsInfo;
import com.mshop.mapper.GoodsInfoMapper;
import com.mshop.service.GoodsService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    @GlobalTransactional // 开启Seata全局事务（分布式事务）
    public BigDecimal decrGoodsStore(Long goodsId, Integer buyNum) {
        UpdateWrapper<GoodsInfo> goodsInfoUW = new UpdateWrapper<>();
        goodsInfoUW.setSql("store=store-" + buyNum).eq("id", goodsId).ge("store", buyNum);
        int row = goodsInfoMapper.update(null, goodsInfoUW);
        if (0 == row) {
            return null;
        }
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsId);
        return goodsInfo.getPrice();
    }

    @Override
    @GlobalTransactional
    public void incrGoodsStore(Long goodsId, Integer buyNum) {
        UpdateWrapper<GoodsInfo> goodsInfoUW = new UpdateWrapper<>();
        goodsInfoUW.setSql("store=store+" + buyNum).eq("id", goodsId);
        goodsInfoMapper.update(null, goodsInfoUW);
    }
}

package com.mshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mshop.domain.Goods;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
}

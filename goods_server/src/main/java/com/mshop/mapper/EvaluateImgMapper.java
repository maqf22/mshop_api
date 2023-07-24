package com.mshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mshop.domain.EvaluateImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EvaluateImgMapper extends BaseMapper<EvaluateImg> {
    @Select("select count(*) from (select `e`.`id` from `g_evaluate_img` `ei` inner join `g_evaluate` `e` on `e`.`id`=`ei`.`evaluate_id` where `e`.`goods_id`=#{goodsId} group by `ei`.`evaluate_id`) `hasImgs`")
    Long getHasImageEvaluateListCount(Long goodsId);
}

package com.mshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mshop.domain.Evaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EvaluateMapper extends BaseMapper<Evaluate> {
    @Select({
            "<script>",
            "select `e`.*, `u`.`nick_name`, `ui`.`avatar`",
            "<if test='evaluateLevel==\"img\"'>,`ei`.`evaluate_id` `eiid`</if>",
            "from g_evaluate e inner join u_user u on e.user_id=u.id left join u_user_info ui on e.user_id=ui.user_id",
            "<if test='evaluateLevel==\"img\"'>inner join `g_evaluate_img` `ei` on `e`.`id`=`ei`.`evaluate_id`</if>",
            "<where>",
            "e.goods_id=#{goodsId}",
            "<if test='evaluateLevel==\"a\"'>and e.score&gt;=4</if>",
            "<if test='evaluateLevel==\"b\"'>and e.score=3</if>",
            "<if test='evaluateLevel==\"c\"'>and e.score&lt;=2</if>",
            "</where>",
            "<if test='evaluateLevel==\"img\"'>group by `eiid`</if>",
            "order by e.time desc limit ${skipNum}, ${pageSize}",
            "</script>"
    })
    List<Map<String, Object>> getEvaluateList(Long goodsId, String evaluateLevel, Long skipNum, Long pageSize);

    @Select("select (select count(*) from `g_evaluate` where `score`>=4 and `goods_id`=#{goodsId}) `A`, (select count(*) from `g_evaluate` where `score`=3 and `goods_id`=#{goodsId}) `B`, (select count(*) from `g_evaluate` where `score`<=2 and `goods_id`=#{goodsId}) `C`, (select count(*) from (select `e`.`id` from `g_evaluate_img` `ei` inner join `g_evaluate` `e` on `e`.`id`=`ei`.`evaluate_id` where `e`.`goods_id`=#{goodsId} group by `ei`.`evaluate_id`) `hasImgs`) `Img`;")
    Map<String, Long> getCountEvaluateNum(Long goodsId);
}

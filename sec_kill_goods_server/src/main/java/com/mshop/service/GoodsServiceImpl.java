package com.mshop.service;

import com.alibaba.fastjson.JSONObject;
import com.mshop.commons.Constants;
//import com.mshop.commons.JsonResult;
import com.mshop.domain.Goods;
import com.mshop.mapper.GoodsMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
//import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public List<Goods> getGoodsList() {
        return goodsMapper.selectList(null);
    }

    /**
     * 秒杀
     *
     * @param userId
     * @param goodsId
     * @return 0-下单成功 1-活动还没开始 2-活动已结束 3-库存不足 4-用户重复下单
     */
    @Override
    public int secKill(Long userId, Long goodsId) {
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(Constants.GOODS_STORE + goodsId);
        long sysTime = System.currentTimeMillis();
        long startTime = Long.parseLong(entries.get("startTime").toString());
        long endTime = Long.parseLong(entries.get("endTime").toString());
        if (sysTime < startTime) {
            return 1; // 活动没开始
        }
        if (sysTime > endTime) {
            return 2; // 活动已结束
        }
        // 定义map集合，需要将这个数据转换成json后，存入MQ通知下单系统下单，存入Redis防止掉单
        HashMap map = new HashMap();
        map.put("uid", userId);
        map.put("goodsId", goodsId);
        map.put("buyPrice", entries.get("buyPrice"));
        String orderJson = JSONObject.toJSONString(map);
        do {
            // 重写execute方法实现Redis事务
            Object result = stringRedisTemplate.execute(new SessionCallback<Object>() {
                @Override
                public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                    // 定义List集合存储需要监控的key
                    // 这里需要监控商品库存防止超卖，监控用户购买记录防止重复购买
                    List keys = new ArrayList<>();
                    keys.add(Constants.GOODS_STORE + goodsId);
                    keys.add(Constants.PURCHASE_LIMITS + goodsId + ":" + userId);
                    // 监近key以后，相当于锁定了key所对应的数据
                    redisOperations.watch(keys);
                    String storeStr = (String) redisOperations.opsForHash().get((K) (Constants.GOODS_STORE + goodsId), (V) "store");
                    int store = Integer.parseInt(storeStr);
                    if (store <= 0) {
                        redisOperations.unwatch(); // 释放监控key
                        return 3; // 库存不足
                    }
                    String purchaseLimit = (String) redisOperations.opsForValue().get((K) (Constants.PURCHASE_LIMITS + goodsId + ":" + userId));
                    if (null != purchaseLimit) {
                        redisOperations.unwatch();
                        return 4; // 用户重复下单
                    }
                    // 到这里表示暂时有库存且用户没有购买记录
                    redisOperations.multi();
                    // 库存-1
                    redisOperations.opsForHash().put((K) (Constants.GOODS_STORE + goodsId), "store", (store - 1) + "");
                    // 添加用户购买记录
                    redisOperations.opsForValue().set((K) (Constants.PURCHASE_LIMITS + goodsId + ":" + userId), (V) "1");
                    // 使用固定的key，使用订单json作为value，使用系统时间戳作为分数，后期通过定时任务定期扫描判断是否存在掉单行为
                    redisOperations.opsForZSet().add((K) (Constants.ORDERS), (V) orderJson, System.currentTimeMillis());
                    // 提交事务返回List，长度大于0表示事务提交成功，否则表示事务提交失败，原因是其他线程提前修改了被监控的key对应的数据
                    return redisOperations.exec();
                }
            });
            // result为Integer类型表示减少库存时出现逻辑错误，直接返回即可
            if (result instanceof Integer) {
                return (Integer) result;
            }
            List list = (List) result;
            // list不为空表示库存减少成功，需要立即结束循环
            if (null != list && !list.isEmpty()) {
                break;
            }
        } while (true);
        // 库存减少成功，用户购买记录添加成功
        amqpTemplate.convertAndSend("secKillExchange", "", orderJson);
        return 0;
    }
}

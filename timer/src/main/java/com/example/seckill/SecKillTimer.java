package com.example.seckill;

import com.example.domain.Goods;
import com.example.service.remote.RemoteSecKillGoodsServer;
import com.mshop.commons.Constants;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Component
public class SecKillTimer {
    @Resource
    private RemoteSecKillGoodsServer remoteSecKillGoodsServer;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private AmqpTemplate amqpTemplate;

    @Scheduled(cron = "0/5 * * * * *")
    public void initSecKillDataToRedis() {
        /*
         * 获取所有的秒杀商品
         * 实际项目不应该获取所有的商品信息，应该是根据系统时间获取即将开始活动的商品
         * */
        List<Goods> goodsList = remoteSecKillGoodsServer.getGoodsList().getResult();
        goodsList.forEach(goods -> {
            stringRedisTemplate.opsForHash().putIfAbsent(Constants.GOODS_STORE + goods.getId(), "store", goods.getStore()+"");
            stringRedisTemplate.opsForHash().putIfAbsent(Constants.GOODS_STORE + goods.getId(), "startTime", goods.getStartTime()+"");
            stringRedisTemplate.opsForHash().putIfAbsent(Constants.GOODS_STORE + goods.getId(), "endTime", goods.getEndTime()+"");
            stringRedisTemplate.opsForHash().putIfAbsent(Constants.GOODS_STORE + goods.getId(), "buyPrice", goods.getPrice()+"");
        });
    }

    @Scheduled(cron = "0/5 * * * * *")
    public void fixOrder() {
        long maxScore = System.currentTimeMillis();
        Set<String> orderSet = stringRedisTemplate.opsForZSet().rangeByScore(Constants.ORDERS, 0, maxScore);
        orderSet.forEach(order -> amqpTemplate.convertAndSend("secKillExchange", "", order));
    }
}

package com.example.timer;

import com.example.service.remote.RemoteOrder;
import com.mshop.commons.JsonResult;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class Timer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RemoteOrder remoteOrder;

    /**
     * 配置定时任务，每5分钟执行一次用于扫描掉单数据
     */
    @Scheduled(cron = "* 0/5 * * * *")
    public void checkOrderPay() {
        // 根据系统时间计算，当前时间5分钟之前的时间毫秒值，作为获取数据的最大分数
        long maxScore = System.currentTimeMillis() - 1000 * 60 * 5;
        // 从ZSet中获取分数从0到当前时间的5分钟之前的所有消息这些消息可能掉单了
        Set<String> orderSet = stringRedisTemplate.opsForZSet().rangeByScore("order", 0, maxScore);
        if (null == orderSet) return;
        orderSet.forEach(order -> {
            System.out.println(order);
            JsonResult jsonResult = remoteOrder.checkOrderPay(order);
            System.out.println(jsonResult.getCode());
        });
    }
}

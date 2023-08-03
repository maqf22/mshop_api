package com.example;

import com.example.domain.Goods;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class TimerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void getTimeTest() {
        Goods goods = new Goods();
        goods.setStartTime(new Date());
        System.out.println(goods.getStartTime());
    }

}

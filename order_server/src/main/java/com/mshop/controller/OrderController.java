package com.mshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.mshop.commons.Code;
import com.mshop.commons.JsonResult;
import com.mshop.config.AlipayConfig;
import com.mshop.domain.Order;
import com.mshop.domain.VConfirmOrderInfo;
import com.mshop.service.OrderService;
import com.mshop.service.remote.RemoteUserService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

@RestController
public class OrderController {
    @Resource
    private RemoteUserService remoteUserService;
    @Resource
    private OrderService orderService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/addOrder")
    public JsonResult<Object> addOrder(Long goodsId, Integer buyNum, String token) {
        JsonResult<Long> jsonRes = remoteUserService.getUserId(token);
        if (jsonRes.getCode().equals(Code.NOT_LOGIN.getCode())) {
            return new JsonResult<>(Code.NOT_LOGIN, null);
        }
        Object res = orderService.addOrder(goodsId, buyNum, jsonRes.getResult());
        if (res.equals("0")) {
            return new JsonResult<>(Code.ERROR, "库存减少失败", null);
        }
        if (res.equals("-1")) {
            return new JsonResult<>(Code.ERROR, "订单添加失败", null);
        }
        if (res.equals("-2")) {
            return new JsonResult<>(Code.ERROR, "订单详情添加失败", null);
        }
        return new JsonResult<>(Code.OK, res);
    }

    @RequestMapping("/confirmOrderInfo")
    public JsonResult<List<VConfirmOrderInfo>> confirmOrderInfo(Long orderId) {
        List<VConfirmOrderInfo> orderInfoList = orderService.getOrderInfoByOrderId(orderId);
        return new JsonResult<>(Code.OK, orderInfoList);
    }

    @RequestMapping("/pay")
    public Object pay(Long orderId, String orderNo, BigDecimal actualMoney, Long addressId, String token, String payType, Integer point) throws AlipayApiException {
        JsonResult<Long> jsonRes = remoteUserService.getUserId(token);
        if (jsonRes.getCode().equals(Code.NOT_LOGIN.getCode())) {
            return new JsonResult<>(Code.NOT_LOGIN, null);
        }
        // 定义Map保合用于存放需要支付订单数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("addressId", addressId);
        map.put("point", point);
        map.put("id", orderId);
        map.put("orderNo", orderNo);
        String orderJson = JSONObject.toJSONString(map);
        // 重写Redis的execute方法，否则不能开启事务，返回一个Object类型的数据表示本次execute方法的执行结果
        stringRedisTemplate.execute(new SessionCallback<Object>() {
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                redisOperations.multi();
                // 将需要支付的订单数据存入Redis，必须指定超时时间
                redisOperations.opsForValue().set((K)("orderPay:" + orderNo), (V)orderJson, Duration.ofSeconds(60 * 30));
                // 存放订单信息到Redis中防止用户支付成功后无法修改订单和无法配送，防止掉单
                // 使用订单的Json作为Value使用系统时间毫秒值作为分数
                // 这个数据不能指定超时时间，需要利用定时任务不断扫描这个数据，判断是否存在掉单，只有确定订单完成后才可删除数据
                redisOperations.opsForZSet().add((K)"order", (V)orderJson, System.currentTimeMillis());
                return redisOperations.exec();
            }
        });
        if (payType.equals("alipay")) {
            return alipay(orderNo, actualMoney);
        }
        return "准备支付";
    }

    @RequestMapping("/return_url")
    public String return_url(String out_trade_no, BigDecimal total_amount) {
        String orderJson = stringRedisTemplate.opsForValue().get("orderPay:" + out_trade_no);
        Order order = JSONObject.parseObject(orderJson, Order.class);
        order.setActualMoney(total_amount);
        order.setStatus("1");
        orderService.orderPay(order);
        return "支付成功";
    }

    private String alipay(String orderNo, BigDecimal actualMoney) throws AlipayApiException {
        // 创建支付宝支付的客户端对象，指定支付宝支付时的公共参数
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        // 创建支付宝请求对象
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        // 设置异步和同步响应地址
        request.setNotifyUrl(AlipayConfig.notify_url);
        request.setReturnUrl(AlipayConfig.return_url);
        // 定义业务参数
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNo);
        bizContent.put("total_amount", actualMoney);
        bizContent.put("subject", "MShop商城支付订单");
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        // 发送请求，获取用户支付页面
        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
            return response.getBody();
        } else {
            System.out.println("调用失败");
        }
        return "";
    }

    @RequestMapping("/checkOrderPay")
    public JsonResult checkOrderPay(String orderJson) throws AlipayApiException {
        // System.out.println(orderJson);
        Order order = JSONObject.parseObject(orderJson, Order.class);
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "  \"out_trade_no\":\""+ order.getOrderNo() +"\" " +
                "}");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            System.out.println(response.getBody());
            JSONObject jsonObject = JSONObject.parseObject(response.getBody());
            JSONObject alipayTradeQueryResponse = (JSONObject) jsonObject.get("alipay_trade_query_response");
            String tradeStatus = alipayTradeQueryResponse.getString("trade_status");
            System.out.println(tradeStatus);
            orderService.checkOrderPay(tradeStatus, order);
        } else {
            System.out.println("调用失败");
        }
        return new JsonResult<>(Code.OK, null);
    }
}

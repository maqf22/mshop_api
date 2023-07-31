package com.mshop.commons;

public final class Constants {
    public Constants() {
    }

    // 商品库存在Redis中的统一Key前缀
    public static final String GOODS_STORE = "GOODS_STORE:";
    // 用户购买记录在Redis中的统一Key前缀
    public static final String PURCHASE_LIMITS = "PURCHASE_LIMITS:";
    // 订单备份数据在Redis中的Key
    public static final String ORDERS = "ORDERS:";
    // 订单结果在Redis中的统一Key前缀
    public static final String ORDERS_RESULT = "ORDERS_RESULT:";
    // 分布式锁在Redis中的Key前缀
    public static final String LOCK = "LOCK:";
}

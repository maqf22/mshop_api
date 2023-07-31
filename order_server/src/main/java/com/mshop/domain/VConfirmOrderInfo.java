package com.mshop.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("v_confirm_order_info")
public class VConfirmOrderInfo {
    private BigDecimal price;
    private Integer amount;
    private Long goodsId;
    private String goodsDescribe;
    private Long orderId;
    private String url;
}

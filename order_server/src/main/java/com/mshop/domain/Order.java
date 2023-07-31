package com.mshop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("o_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    private BigDecimal orderMoney;
    private BigDecimal actualMoney;
    private Integer point;
    private String status;
    private Long createTime;
    private Long userId;
    private Long addressId;
    private String orderNo;
}

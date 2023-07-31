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
@TableName("o_order_info")
public class OrderInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private BigDecimal price;
    private Integer amount;
    private Long goodsId;
    private Long orderId;
}

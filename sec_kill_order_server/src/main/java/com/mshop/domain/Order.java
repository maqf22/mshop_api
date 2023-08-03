package com.mshop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("s_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long uid;
    private Long goodsId;
    private Integer buyNum;
    private BigDecimal buyPrice;
    private BigDecimal orderMoney;
    private Date createTime;
    private Integer status;
}

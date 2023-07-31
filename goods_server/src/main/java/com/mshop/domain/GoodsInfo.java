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
@TableName("g_goods_info")
public class GoodsInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String goodsDescribe;
    private Integer store;
    private String createTime;
    private BigDecimal price;
    private BigDecimal cost;
    private String publishStatus;
    private String auditStatus;
    private String productionDate;
    private String shelfLife;
    private Long goodsId;
    private String version;
}

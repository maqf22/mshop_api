package com.mshop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("s_goods")
public class Goods {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String nameDesc;
    private BigDecimal price;
    private Integer store;
    private Date startTime;
    private Date endTime;
}

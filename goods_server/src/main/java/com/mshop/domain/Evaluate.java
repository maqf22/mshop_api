package com.mshop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("g_evaluate")
public class Evaluate {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String content;
    private Integer score;
    private Long time;
    private Long userId;
    private Long goodsId;
}

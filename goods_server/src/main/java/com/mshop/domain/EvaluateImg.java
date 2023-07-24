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
@TableName("g_evaluate_img")
public class EvaluateImg {
    @TableId(type= IdType.AUTO)
    private Long id;
    private String imageUrl;
    private Long evaluateId;
}

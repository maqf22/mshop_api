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
@TableName("u_address")
public class Address {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String province;
    private String city;
    private String street;
    private String phone;
    private String name;
    private Long userId;
    private String isDefault;
}

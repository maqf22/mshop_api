package com.mshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mshop.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.checkerframework.checker.signature.qual.Identifier;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

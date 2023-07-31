package com.mshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mshop.domain.Address;
import com.mshop.domain.User;
import com.mshop.mapper.AddressMapper;
import com.mshop.mapper.UserMapper;
import com.mshop.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private AddressMapper addressMapper;

    @Override
    public int login(User user) {
        QueryWrapper<User> userQW = new QueryWrapper<>();
        userQW.eq("phone", user.getPhone());
        User dbUser = userMapper.selectOne(userQW);
        if (null == dbUser) {
            return 1;
        }
        if (!dbUser.getPassword().equals(user.getPassword())) {
            return 2;
        }
        // 登录成功后将dbUser的数据复制到user中
        BeanUtils.copyProperties(dbUser, user);
        return 0;
    }

    @Override
    public int regUser(User user) {
        try {
            // 插入数据，这行可以会抛出异常DuplicateKeyException，表示违反数据库的唯一约束，证明手机已存在
            // （从概率上来考虑手机号重复的可能性比较低，所以不去查询数据库减少数据库IO）
            userMapper.insert(user);
        } catch (DuplicateKeyException e) {
            return 1;
        }
        return 0;
    }

    @Override
    public List<Address> getUserAddressByUserId(Long userId) {
        QueryWrapper<Address> addressQW = new QueryWrapper<>();
        addressQW.eq("user_id", userId).orderByDesc("is_default");
        return addressMapper.selectList(addressQW);
    }
}

package com.mshop.service;

import com.mshop.domain.Address;
import com.mshop.domain.User;

import java.util.List;

public interface UserService {
    int login(User user);

    int regUser(User user);

    List<Address> getUserAddressByUserId(Long userId);
}

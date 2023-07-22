package com.mshop.service;

import com.mshop.domain.User;

public interface UserService {
    int login(User user);

    int regUser(User user);
}

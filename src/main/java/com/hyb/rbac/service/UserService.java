package com.hyb.rbac.service;

import com.hyb.rbac.repo.User;

public interface UserService {
    User getUserByName(String name);
    void addUser(User user);
    int updateUser(User user);
    int deleteUser(int id);
}

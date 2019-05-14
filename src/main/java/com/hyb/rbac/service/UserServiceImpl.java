package com.hyb.rbac.service;

import com.hyb.rbac.common.util.Md5Util;
import com.hyb.rbac.mapper.UserMapper;
import com.hyb.rbac.repo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    public User getUserByName(String name){
        return userMapper.getOneByName(name);
    }
    public void addUser(User user){
        user.setPassword(Md5Util.getMd5(user.getPassword()));
        userMapper.addOne(user);
    }

    @Override
    public int updateUser(User user) {
        user.setPassword(Md5Util.getMd5(user.getPassword()));
        user.setUpdatedTime(new Date());
        return userMapper.update(user);
    }

    @Override
    public int deleteUser(int id) {
        return userMapper.deleteById(id);
    }
}

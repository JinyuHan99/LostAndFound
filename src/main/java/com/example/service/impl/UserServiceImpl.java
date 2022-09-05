package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public boolean register(User user) {
        return  userDao.add(user)>0;
    }

    public User login(User user){
        User user1 = userDao.getByAll(user);
    //怎么做登陆验证：拦截器？shiro？
        if(user1!=null){
            return user1;
        }
        return null;
    }

    @Override
    public boolean update(User user) {
        return userDao.update(user)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return userDao.delete(id)>0;
    }

    @Override
    public User getById(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getByUsername(String userame) {
        return userDao.getByUsername(userame);
    }
}

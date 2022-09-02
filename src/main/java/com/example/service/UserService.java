package com.example.service;


import com.example.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserService {
    /**
     * 保存
     *
     * @param user
     * @return
     */
    public boolean register(User user);

    public User login(User user);

    /**
     * 修改
     *
     * @param user
     * @return
     */
    public boolean update(User user);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public boolean delete(Integer id);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public User getById(Integer id);


    public List<User> getAll();
}


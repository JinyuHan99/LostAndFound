package com.example.dao;


import com.example.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface UserDao {

    //以下为mybatis开发使用的代码
    @Insert("insert into user values(null,#{username},#{password})")
    int add(User user);

    @Update("update user set username=#{username},password=#{password} where id=#{id}")
    int update(User user);

    @Delete("delete from user where id=#{id}")
    int delete(Integer id);

    @Select("select * from user where id=#{id}")
    User getById(Integer id);

    @Select("select * from user")
    List<User> getAll();

    @Select("select * from user where username=#{username} and password=#{password}")
    User getByAll(User user);

    @Select("select * from user where username=#{username}")
    User getByUsername(String userame);


}

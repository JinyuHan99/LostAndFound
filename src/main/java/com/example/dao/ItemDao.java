package com.example.dao;


import com.example.domain.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemDao {

    //以下为mybatis开发使用的代码
    @Insert("insert into item values(null,#{type},#{name},#{description},#{contact_user_id})") //对应item中的属性；若要对应表的字段名，可以按照sql格式写
    int save(Item item);

    @Update("update item set type=#{type},name=#{name},description=#{description} where id=#{id}")
    int update(Item item);

    @Delete("delete from item where id=#{id}")
    int delete(Integer id);

    @Select("select * from item where id=#{id}")
    Item getById(Integer id);

    @Select("select item.id,item.type,item.name,item.description,item.contact_user_id,user.username as contact_username from item right join user on user.id=item.contact_user_id")
    List<Item> getAll();

    @Select("select * from item where name like '%${name}%'")
    List<Item> getByName(String name);
}


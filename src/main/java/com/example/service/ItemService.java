package com.example.service;

import com.example.domain.Item;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//提供业务层接口
@Transactional
public interface ItemService {
    /**
     * 保存
     * @param item
     * @return
     */
    public boolean save(Item item);

    /**
     * 修改
     * @param item
     * @return
     */
    public boolean update(Item item);

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(Integer id);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    public Item getById(Integer id);

    /**
     * 查询所有
     * @return
     */
    public List<Item> getAll();

    public List<Item> getByName(String name);
}

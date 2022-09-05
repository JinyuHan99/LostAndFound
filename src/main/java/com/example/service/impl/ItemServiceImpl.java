package com.example.service.impl;

import com.example.dao.ItemDao;
import com.example.domain.Item;
import com.example.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;

    @Override
    public boolean save(Item item) {
        return  itemDao.save(item)>0;
    }

    @Override
    public boolean update(Item item) {
        return itemDao.update(item)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return itemDao.delete(id)>0;
    }

    @Override
    public Item getById(Integer id) {
        return itemDao.getById(id);
    }

    @Override
    public List<Item> getAll() {
        return itemDao.getAll();
    }

    @Override
    public List<Item> getByName(String name) {
        return itemDao.getByName(name);
    }

    @Override
    public List<Item> getByContactUserId(Integer contact_user_id) {
        return itemDao.getByContactUserId(contact_user_id);
    }

    @Override
    public List<Item> getMyByName(String name, Integer contact_user_id) {
        return itemDao.getMyByName(name,contact_user_id);
    }
}

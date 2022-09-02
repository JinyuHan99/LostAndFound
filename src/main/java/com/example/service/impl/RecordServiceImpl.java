package com.example.service.impl;

import com.example.dao.RecordDao;
import com.example.domain.Record;
import com.example.service.RecordService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService{
    @Autowired
    private RecordDao recordDao;

    @Override
    public List<Record> get(int fromUserID, int toUserID) {
        List<Record> records= recordDao.get(fromUserID,toUserID);
        return records;
    }

    @Override
    public boolean save(Record record) {
        return recordDao.save(record)>0;
    }

    @Override
    public List<Record> findUnread(long userID, long toUserID) {
        List<Record> records = recordDao.findUnread(userID,toUserID);
        return records;
    }
}

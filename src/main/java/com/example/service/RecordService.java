package com.example.service;

import com.example.domain.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecordService {

    List<Record> get(int fromUserID, int toUserID);


    boolean save(Record record);


    List<Record> findUnread(long userID, long toUserID);



}

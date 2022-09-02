package com.example.dao;

import com.example.domain.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


import java.util.List;

@Mapper
public interface RecordDao {
    @Select("select * from record where (from_user=#{userID} and to_user=#{toUserID}) or (from_user=#{toUserID} and to_user=#{userID})")
    List<Record> get(int userID, int toUserID);

    @Insert("insert into record values(null,#{content},#{created},#{from_user},#{to_user},#{used})")
    int save(Record record);

    @Select("select * from record where to_user=#{toUserID} and from_user=#{userID} and used=0")
    List<Record> findUnread(long userID, long toUserID);


}

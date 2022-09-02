package com.example.redisreceiver;

import com.example.domain.Record;
import com.example.dao.UserDao;
import com.example.service.RecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@EnableScheduling
@Component
public class RedisReceiver{

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserDao userDao;
    @Autowired
    RecordService recordService;

    public static class SessionInfo {
        public Session session;
        public long userId;
        public long toUserId;
        public SessionInfo(Session session, long userid) {
            this.session = session;
            this.userId = userid;
        }
    }

    ObjectMapper objectMapper = new ObjectMapper();

    public List<SessionInfo> list = new LinkedList();
    public boolean isUserOnline(long userid){
        return true;
    }

    public void receiveMessage(String m){
        System.out.println(m);
    }


    public void send(Session session, String m){}

    public void sendOnlineOrOffline(long userid,String m){};

    public void sendToUserid(long userid, String m){};

    public void onOpen(Session session, long userid,long toUserId) {
        if (isUserOnline(userid)) {
            try {
                System.out.println("已经在线");
                send(session, "已经在线");
                session.close();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        synchronized (list) {
            list.add(new SessionInfo(session, userid));
            System.out.println("有人上线了：" + list.size());
            stringRedisTemplate.opsForValue().setBit("ONLINE", userid, true);
            sendOnlineOrOffline(userid, "online");
        }

        //推送离线消息
        List<Record> records = recordService.findUnread(userid,toUserId);
        records.forEach(record -> {
            try {
                sendToUserid(userid, record.getContent());
                record.setUsed(true);
                recordService.save(record);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}



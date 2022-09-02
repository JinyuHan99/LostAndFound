package com.example.websocketserver;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.domain.Record;
import com.example.listener.SubscribeListener;
import com.example.service.RecordService;
import com.example.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * 使用springboot的唯一区别是要@Component声明下，而使用独立容器是由容器自己管理websocket的，但在springboot中连容器都是spring管理的。
 * 虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
 */

@Slf4j
@Component
@ServerEndpoint(value = "/api/websocket/{userID}/{toUserID}")
public class WebSocketServer {
    /**
     * 因为@ServerEndpoint不支持注入，所以使用SpringUtils获取IOC实例
     * recordService使用 setRecordService注入
     */
    private RedisMessageListenerContainer redisMessageListenerContainer = SpringUtils.getBean(RedisMessageListenerContainer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    // private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收sid
    private String userID = "";
    private String toUserID = "";

    private SubscribeListener subscribeListener;
    private ApplicationContext applicationContext;
    private static RecordService recordService;
    @Autowired
    public void setRecordService(RecordService recordService){
        WebSocketServer.recordService = recordService;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userID") String userID, @PathParam("toUserID") String toUserID) {
        log.info(String.valueOf(session.getRequestURI()));
        this.session = session;
        this.userID = userID;
        this.toUserID = toUserID;
        webSocketSet.add(this);     // 加入set中
        addOnlineCount();           // 在线数加1
        subscribeListener = new SubscribeListener();
        subscribeListener.setSession(session);
        List<Topic> list = new ArrayList<>();
        list.add(new PatternTopic("test"));
        //设置订阅topic
        redisMessageListenerContainer.addMessageListener(subscribeListener, list);
        //发送连接信息
        try {
            sendMessage("{\"msg\":\"Connection succeeded. Loading chat history...\",\"code\":\"system\"}"); //服务器主动推送的消息
            log.info("有新客户端开始监听,sid=" + userID + ",当前在线人数为:" + getOnlineCount());
        } catch (IOException e) {
            log.error("websocket IO Exception");
        }
        //发送聊天记录
        List<Record> messageRecords = recordService.get(Integer.parseInt(userID),Integer.parseInt(toUserID));
        String toJSONString = JSONObject.toJSONString(messageRecords);



        try {
            sendMessage(toJSONString);
            log.info("成功发送聊天记录");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  // 从set中删除
        subOnlineCount();              // 在线数减1
        redisMessageListenerContainer.removeMessageListener(subscribeListener);
        // 断开连接情况下，更新主板占用情况为释放
        log.info("释放的sid=" + userID + "的客户端");
        releaseResource();
    }

    private void releaseResource() {
        // 这里写释放资源和要处理的业务
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自客户端 sid=" + userID + " 的信息:" + message);
        log.info("推送到：" + this.toUserID);
        Date date = new Date();
        boolean flag = false;
        //收到消息后发送消息
        try {
            flag = sendMessage(message, toUserID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //记录消息
        Record record = new Record();
        record.setFrom_user(Long.parseLong(userID));
        record.setTo_user(Long.parseLong(toUserID));
        record.setCreated(date);
        JSONObject jsonObject = JSONObject.parseObject(message);
        String msg = jsonObject.getString("msg");
        record.setContent(msg);

        if(flag == false){
            System.out.println("发送消息失败");
            record.setUsed(false);
            recordService.save(record);
            //存储消息，标记为未读
        }else{
            System.out.println("发送消息成功");
            record.setUsed(true);
            recordService.save(record);
            //存储消息，标记为已读
        }
    }

    /**
     * 发生错误回调
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(session.getBasicRemote() + "客户端发生错误");
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     */
    public static boolean sendMessage(String message, String toUserID) throws IOException {
        log.info("推送消息到客户端 " + toUserID + "，推送内容:" + message);
        JSONObject jsonObject = JSONObject.parseObject(message);
        jsonObject.put("code","message");
        for (WebSocketServer item : webSocketSet) {
            try {
                if (toUserID.equals(item.userID)) {
                    item.sendMessage(jsonObject.toJSONString());
                    return true;
                }
            } catch (IOException e) {
                continue;
            }
        }

        return false;
    }

    /**
     * 实现服务器主动推送消息到 指定客户端
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 获取当前在线人数
     *
     * @return
     */
    public static int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 当前在线人数 +1
     *
     * @return
     */
    public static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    /**
     * 当前在线人数 -1
     *
     * @return
     */
    public static void subOnlineCount() {
        onlineCount.getAndDecrement();
    }

    /**
     * 获取当前在线客户端对应的WebSocket对象
     *
     * @return
     */
    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }
}


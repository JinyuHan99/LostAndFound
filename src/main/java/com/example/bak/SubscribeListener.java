//package com.example.listener;
//
//
//import com.example.websocketserver.WebSocketServer;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//
//import javax.annotation.Resource;
//import javax.websocket.Session;
//import java.io.IOException;
//
///**
// *  描述：消息订阅监听类
// */
//public class SubscribeListener implements MessageListener {
//
//    @Resource
//    private WebSocketServer webSocketServer;
//
//    private Session session;
//    public Session getSession() {   return session;  }
//    public void setSession(Session session) {
//        this.session = session;
//    }
//
//    /**
//     * 接收发布者的消息
//     */
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        String msg = new String(message.getBody());
//        System.out.println(new String(pattern) + "主题发布：" + msg);
//        if (null != session && session.isOpen()) {
//            try {
//                session.getBasicRemote().sendText(msg);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

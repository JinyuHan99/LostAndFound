//package com.example.config;
//
//import com.example.redisreceiver.RedisReceiver;
//import com.example.listener.SubscribeListener;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.listener.PatternTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.Topic;
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * 注入spring容器中，在项目启动的时候就会加载，理解成这个类的对象已经创建好了
// * 放在spring容器当中了
// */
////@Configuration
//public class RedisConfig {
//    /**
//     * 需要手动注册RedisMessageListenerContainer加入IOC容器
//     * @return
//     */
//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        List<Topic> list = new ArrayList<>();
//        list.add(new PatternTopic("chat"));
//        container.addMessageListener(new SubscribeListener(),list);
//        return container;
//    }
//    //消息监听适配器
//    @Bean
//    MessageListenerAdapter listenerAdapter(RedisReceiver receiver) {
//        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
//        //也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }
//
//    /**redis 读取内容的template */
//    @Bean(name="stringRedisTemplate")
//    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
//        return new StringRedisTemplate(connectionFactory);
//    }
//
//
//}

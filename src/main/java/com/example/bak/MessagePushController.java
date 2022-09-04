//package com.example.bak;
//
//import com.example.websocketserver.WebSocketServer;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//
////描述 WebSocket消息推送控制器
////@RestController
////@RequestMapping("/api/socket")
//public class MessagePushController {
//    //页面请求
////    @GetMapping("/index/{username}")
////    public ModelAndView socket(@PathVariable String username) {
////        ModelAndView mav = new ModelAndView("/socket1");
////        mav.addObject("username", username);
////        return mav;
////    }
//
//    //推送数据接口
//    // 推送数据到websocket客户端 接口
//    @GetMapping("/socket/push/{cid}")
//    public Map pushMessage(@PathVariable("cid") String cid, String message) {
//        Map<String, Object> result = new HashMap<>();
//        try {
//            WebSocketServer.sendMessage("服务端推送消息：" + message, cid);
//            result.put("code", cid);
//            result.put("msg", message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//
//}

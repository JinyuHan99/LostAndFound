package com.example.controller;

import com.example.domain.Item;
import com.example.domain.User;
import com.example.exception.SystemException;
import com.example.service.ItemService;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.example.controller.Code.SELECTBYNAME_ERR;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Result save(@RequestBody Item item, HttpSession session) {
//        System.out.println(session.getAttribute("username").toString());
        String username = session.getAttribute("username").toString();
        User user = userService.getByUsername(username);
        if(user!=null){
            item.setContact_user_id(user.getId());
        }else{
            throw new SystemException("系统异常", new RuntimeException("用户不存在"),SELECTBYNAME_ERR);
        }
        boolean flag = itemService.save(item);
        return  new Result((flag?Code.SAVE_OK: Code.SAVE_ERR),flag);
    }

    @PutMapping
    public Result update(@RequestBody Item item) {
        boolean flag = itemService.update(item);
        return  new Result((flag?Code.UPDATE_OK: Code.UPDATE_ERR),flag);

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean flag = itemService.delete(id);
        return  new Result((flag?Code.DELETE_OK: Code.DELETE_ERR),flag);

    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Item item = itemService.getById(id);
//        try{
//            int i = 1/0;
//        }catch(Exception e){
//           throw new SystemException("服务器访问超时请重试",e,Code.SYSTEM_TIME_OUT_ERR);

//        }
        Integer code = item!= null? Code.SELECTBYID_OK:Code.SELECTBYID_ERR;
        String s = item!= null? "":"查询失败";
        return  new Result(code,item,s);

    }

    @GetMapping
    public Result getAll() {
        List<Item> itemList = itemService.getAll();
        Integer code = itemList!= null? Code.SELECTALL_OK:Code.SELECTALL_ERR;
        String s = itemList!= null? "":"查询失败";
        return  new Result(code,itemList,s);
    }

    @GetMapping("/my")
    public Result getAllMyItems(HttpSession session) {
        String username = session.getAttribute("username").toString();
        User user = userService.getByUsername(username);
        List<Item> itemList = itemService.getByContactUserId(user.getId());
        Integer code = itemList!= null? Code.SELECTALL_OK:Code.SELECTALL_ERR;
        String s = itemList!= null? "":"查询失败";
        return  new Result(code,itemList,s);
    }
    @GetMapping("/byName/{name}")
    public Result getByName(@PathVariable String name){
        List<Item> itemList = itemService.getByName(name);
        Integer code = itemList!= null? Code.SELECTBYNAME_OK: SELECTBYNAME_ERR;
        String s = itemList!= null? "":"查询失败";
        return  new Result(code,itemList,s);
    }

    @GetMapping("/my/byName/{name}")
    public Result getMyByName(@PathVariable String name,HttpSession session){
        String username = session.getAttribute("username").toString();
        User user = userService.getByUsername(username);
        List<Item> itemList = itemService.getMyByName(name,user.getId());
        Integer code = itemList!= null? Code.SELECTBYNAME_OK: SELECTBYNAME_ERR;
        String s = itemList!= null? "":"查询失败";
        return  new Result(code,itemList,s);
    }
}

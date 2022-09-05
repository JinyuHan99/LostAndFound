package com.example.controller;

import com.example.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        User userCheck= userService.getByUsername(user.getUsername());
        if(userCheck!=null){
            return new Result(Code.USERNAME_EXIST_ERR,false,"用户名已存在，请重新输入");
        }
        boolean flag = userService.register(user);
        return  new Result((flag?Code.REGISTER_OK: Code.REGISTER_ERR),flag);
    }

    @PostMapping("/login")
    public  Result login(@RequestBody User user, HttpSession session, HttpServletResponse response){
        User user_exist = userService.login(user);
        if(user_exist!=null){
            session.setAttribute("username",user_exist.getUsername());
            Cookie cookie = new Cookie("userID",""+user_exist.getId());
            Cookie cookie1 = new Cookie("userName",user_exist.getUsername());
            cookie1.setPath("/");
            cookie.setPath("/");
            response.addCookie(cookie);
            response.addCookie(cookie1);
            System.out.println(cookie.getValue());
            System.out.println(cookie1.getValue());
            return new Result(Code.LOGIN_OK,true,"登录成功");
        }else{
            return new Result(Code.LOGIN_ERR,false,"用户名或密码错误");
        }

    }

//    @PutMapping("/update")
//    public Result update(@RequestBody User user) {
//        boolean flag = userService.update(user);
//        return  new Result((flag?Code.UPDATE_OK: Code.UPDATE_ERR),flag);
//
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public Result delete(@PathVariable Integer id) {
//        boolean flag = userService.delete(id);
//        return  new Result((flag?Code.DELETE_OK: Code.DELETE_ERR),flag);
//
//    }

//    @GetMapping("/{id}")
//    public Result getById(@PathVariable Integer id) {
//        User user = userService.getById(id);
//        try{
//            int i = 1/0;
//        }catch(Exception e){
//           throw new SystemException("服务器访问超时请重试",e,Code.SYSTEM_TIME_OUT_ERR);

//        }
//        Integer code = user!= null? Code.SELECTBYID_OK:Code.SELECTBYID_ERR;
//        String s = user!= null? "":"查询失败";
//        return  new Result(code,user,s);
//
//    }

//    @GetMapping("/all")
//    public Result getAll() {
//        List<User> userList = userService.getAll();
//        Integer code = userList!= null? Code.SELECTALL_OK:Code.SELECTALL_ERR;
//        String s = userList!= null? "":"查询失败";
//        return  new Result(code,userList,s);
//    }
}

package org.example.backend.Controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.example.backend.Entity.pojo.RestBean;
import org.example.backend.Entity.pojo.User;
import org.example.backend.Service.UserService;
import org.example.backend.utils.JWTUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Resource
    UserService userService;

    @PostMapping("login")
    public RestBean<String> login(
                                @RequestParam String username,
                                @RequestParam String password,
                                HttpSession session
    ){
        User loginUser=userService.login(username,password);
        if(loginUser == null)
        {
            return RestBean.failure(401,"账号或密码错误");
        }else{
            loginUser.setPassword("*");
            session.setAttribute("user",loginUser);
            return RestBean.success("登入成功", JWTUtil.createToken(loginUser));
        }
    }

    @PostMapping("register")
    public RestBean<String> register(@RequestParam String name,
                                     @RequestParam String username,
                                     @RequestParam String password,
                                     @RequestParam String email
    ){
        if (name == null||name.isEmpty())
        {
        return RestBean.failure(400,"用户名不能为空");
        }
        if (username == null||username.isEmpty())
        {
            return RestBean.failure(400,"账号不能为空");
        }
        if(password ==null||password.isEmpty())
        {
            return RestBean.failure(400,"密码不能为空");
        }
        if (email==null||email.isEmpty())
        {
            return RestBean.failure(400,"邮箱不能为空");
        }
        if (userService.login(username,password)!=null)
        {
            return RestBean.failure(400,"用户已存在");
        }
        try {
            if(userService.createUser(name,username,password,email))
            {
                return RestBean.success("注册成功");
            }else {
                return RestBean.failure(500,"注册失败，请联系管理员");
            }
        }catch (Exception e){
            return RestBean.failure(500,"服务器内部错误");
        }

    }


}

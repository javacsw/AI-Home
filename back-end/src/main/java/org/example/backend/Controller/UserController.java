package org.example.backend.Controller;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.example.backend.Entity.pojo.RestBean;
import org.example.backend.Entity.pojo.User;


import org.example.backend.Service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    UserService userService;


    @GetMapping("myinfo")
    public RestBean<User> getMyInfo(HttpSession session){
        if(session.getAttribute("user")==null) return RestBean.failure(401,"未登入");
        User theUser=(User) session.getAttribute("user");
        return RestBean.success("成功",theUser);
    }

    @GetMapping("logout")
    public RestBean<String> logout(HttpSession session){
        User logoinUser=(User) session.getAttribute("user");
        if(logoinUser==null)return RestBean.failure(401,"您尚未登入");
        else{
            session.removeAttribute("user");
            session.invalidate();
        }
        return RestBean.success("登出成功");
    }

    @GetMapping("/AllUser")
    public RestBean<List<User>> getAllUser(){
        List<User> users=userService.getAllUser();
        for(User user:users){
            user.setPassword("*");
        }
        return RestBean.success("cg",users);
    }

    @GetMapping("/getAvatar")
    public RestBean<String> getAvatarById(@RequestParam("id")Integer id){
        String avatar=userService.getAvatar(id);
        return RestBean.success("cg",avatar);
    }

    @GetMapping("/getUserById")
    public RestBean<User> getUserById(@RequestParam("id") Integer id){

        User user=userService.getUserById(id);
        user.setPassword("*");
        return RestBean.success("cg",user);
    }

    @PostMapping("/update")
    public RestBean<String> updateUser(@RequestParam("id") Integer id,
                                       @RequestParam("name") String name,
                                       @RequestParam("signature") String signature,
                                       @RequestParam("email") String email,
                                       @RequestParam("phone") String phone,
                                       HttpSession session) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setSignature(signature);
        user.setEmail(email);
        user.setPhone(phone);
        if (userService.updateProfile(user)) {
            // 同步更新Session中的用户信息
            User currentUser = (User) session.getAttribute("user");
            if (currentUser != null && currentUser.getId().equals(id)) {
                currentUser.setName(name);
                currentUser.setSignature(signature);
                currentUser.setEmail(email);
                currentUser.setPhone(phone);
                session.setAttribute("user", currentUser);
            }
            return RestBean.success("更新成功");
        } else {
            return RestBean.failure(500, "更新失败");
        }
    }



}

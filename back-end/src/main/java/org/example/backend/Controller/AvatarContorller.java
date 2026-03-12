package org.example.backend.Controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.example.backend.Entity.pojo.RestBean;
import org.example.backend.Entity.pojo.User;
import org.example.backend.Service.StorageService;
import org.example.backend.Service.UserService;
import org.example.backend.utils.JWTUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AvatarContorller {
    @Resource
    UserService userService;

    @Resource
    StorageService storageService;

    @PostMapping("/avatar")
    public RestBean<String> upavatar(
            @RequestParam("id") Integer id,
            @RequestParam("avatar") MultipartFile avatar,
            HttpSession session
    ){
        try {
            String avatarUrl = storageService.uploadFile(avatar, "avatars");
            int upavatar = userService.upavatar(id, avatarUrl);
            if (upavatar != 0) {
                // 同步更新Session中的用户信息
                User currentUser = (User) session.getAttribute("user");
                if (currentUser != null && currentUser.getId().equals(id)) {
                    currentUser.setAvator(avatarUrl);
                    session.setAttribute("user", currentUser);
                }
                return RestBean.success("更换成功～", avatarUrl);
            } else {
                return RestBean.failure(503, "数据库更新失败，请联系管理员");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return RestBean.failure(500, "文件上传失败，请联系管理员");
        }
    }
}


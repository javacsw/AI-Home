package org.example.backend.Service.Impl;

import jakarta.annotation.Resource;
import org.example.backend.Entity.pojo.User;
import org.example.backend.Mapper.UserMapper;
import org.example.backend.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Override
    public User login(String username, String password) {
        return userMapper.getUserByUsernameAndPassword(username,password);
    }

    @Override
    public int upavatar(Integer id,String avatar) {
        return userMapper.updateAvatar(id,avatar);
    }

    @Override
    public boolean createUser(String name,String username, String password,String email) {
        return userMapper.createUser(name,username,password,email);
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public String getAvatar(int id) {
        return userMapper.getAvatar(id);
    }

    @Override
    public boolean updateProfile(User user) {
        return userMapper.updateProfile(user) > 0;
    }
}

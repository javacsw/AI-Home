package org.example.backend.Service;

import org.example.backend.Entity.pojo.User;

import java.util.List;

public interface UserService {
    User login(String username, String password);
    int upavatar(Integer id,String avatar);

    boolean createUser(String name,String username, String password, String email);

    User getUserById(int id);

    List<User> getAllUser();

    String getAvatar(int id);

    boolean updateProfile(User user);
}

package org.example.backend.Entity.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String avator;
    private String signature;
    private String name;
    private String phone;
    private Integer role ;

    public User() {
    }
}

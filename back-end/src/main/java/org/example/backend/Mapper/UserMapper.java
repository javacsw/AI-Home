package org.example.backend.Mapper;

import org.apache.ibatis.annotations.*;
import org.example.backend.Entity.pojo.User;

import java.util.List;

@Mapper

public interface UserMapper {
    @Select("SELECT  * FROM tb_users WHERE username = #{username} AND password=#{password}")
    User getUserByUsernameAndPassword(String username,String password);

    @Select("SELECT name FROM tb_users WHERE id=#{id}")
    String getUsernameById(Integer id);

    @Insert("INSERT INTO tb_users(name,username,password,email) VALUES(#{name},#{username},#{password},#{email}) ")
    boolean createUser(String name,String username,String password,String email);

    @Select("SELECT avator FROM  tb_users WHERE id=#{id}")
    String getAvatorById(Integer id);

    //通过id获取用户信息
    @Select("SELECT *FROM tb_users WHERE id=#{id}")
    User getUserById(int id);

    //获取全部的用户信息
    @Select("SELECT *FROM tb_users")
    List<User> getAllUser();

    //获取头像
    @Select("SELECT avator FROM tb_users WHERE id=#{id}")
    String getAvatar(int id);

    // UserMapper.java
    @Update("UPDATE tb_users SET avator = #{avatar} WHERE id = #{id}")
    int updateAvatar(@Param("id") Integer id, @Param("avatar") String avatar);

    @Update("UPDATE tb_users SET name = #{name}, signature = #{signature}, email = #{email}, phone = #{phone} WHERE id = #{id}")
    int updateProfile(User user);
}

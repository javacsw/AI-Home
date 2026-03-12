package org.example.backend.Entity.vo;

import lombok.Data;
import org.example.backend.Entity.pojo.Blog;

//View Object
@Data
public class BlogVO {
    private Blog blog;
    private String username,avator;

    public BlogVO(Blog blog, String username,String avator) {
        this.blog = blog;
        this.username = username;
        this.avator=avator;
    }

}

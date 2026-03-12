package org.example.backend.Service;


import org.example.backend.Entity.pojo.Blog;
import org.example.backend.Entity.vo.BlogVO;

import java.util.List;

public interface BlogService {
    int addNewBlog(Blog blog);
    List<BlogVO> getOrderByBlogs();

    List<BlogVO> getMyBlogs(int authorId);

    BlogVO getBlogById(int id);

    int deleteBlogById(int id);

   
}

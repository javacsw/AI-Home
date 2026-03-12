package org.example.backend.Mapper;

import org.apache.ibatis.annotations.*;
import org.example.backend.Entity.pojo.Blog;

import java.util.List;

@Mapper
public interface BlogMapper {
    @Insert("INSERT INTO tb_blogs(title,authorId,updateDate,content)VALUES(#{title},#{authorId},#{updateDate},#{content})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertNewBlog(Blog blog);

    @Select("SELECT id,authorId,title,substring(content,1,200) AS content FROM tb_blogs ORDER BY id DESC LIMIT 50")
    List<Blog> getBlogsOrderById();

    @Select("SELECT * FROM tb_blogs WHERE authorId=#{authorId} ORDER BY id DESC")
    List<Blog> getMyBlog(int authorId);

    @Select("SELECT * FROM tb_blogs WHERE id=#{id}")
    Blog getBlogById(int id);

    @Delete("DELETE FROM tb_blogs WHERE id=#{id}")
    int deleteBlogById(int id);
}

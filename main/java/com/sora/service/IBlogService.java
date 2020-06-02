package com.sora.service;

import com.sora.domain.Blog;
import com.sora.domain.Course;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/3 22:06
 */
public interface IBlogService {
    public int addBlog(Blog blog)throws Exception;

    public int addBlog(Blog blog, MultipartFile source) throws Exception;

    public List<Blog> findMyRelativedBlogs(long id) throws Exception;

    public List<Blog> findMyRelativedBlogsByKey(long id,String key) throws Exception;

    public List<Blog> findRelativedBlogsByUserIdAndCourseId(long userId,long courseId) throws Exception;

    public List<Blog> findStudentsBlogsByCourseId(long courseId) throws Exception;

    public List<Blog> findBlogsByUserId(long userId) throws Exception;

    public int deleteBlog(Blog blog) throws Exception;
}

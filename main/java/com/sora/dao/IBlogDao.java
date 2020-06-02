package com.sora.dao;

import com.sora.domain.Blog;
import com.sora.domain.Course;
import com.sora.domain.Keyword;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/3 22:06
 */
@Repository
public interface IBlogDao {
    public int addBlog(Blog blog);

    public int addSource(Blog blog);

    public List<Blog> findAllRelativedTeacherBlogByUserId(long userId);

    public List<Blog> findAllRelativedStudentBlogByUserId(long userId);

    public List<Blog> findAllRelativedTeacherBlogByUserIdAndKey(long userId,String key);

    public List<Blog> findAllRelativedStudentBlogByUserIdAndKey(long userId,String key);

    public List<Blog> findRelativedTeacherBlogsByUserIdAndCourseId(long userId,long courseId);

    public List<Blog> findRelativedStudentBlogsByUserIdAndCourseId(long userId,long courseId);

    public List<Blog> findStudentsBlogsByCourseId(long courseId);

    public List<Blog> findStudentBlogsByUserId(long userId);

    public List<Blog> findTeacherBlogsByUserId(long userId);

    public int deleteBlog(Blog blog);

    public int addKeyword(Keyword keyword,Blog blog);
}

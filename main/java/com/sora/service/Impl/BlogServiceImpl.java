package com.sora.service.Impl;

import com.sora.dao.IBlogDao;
import com.sora.domain.Blog;
import com.sora.domain.Course;
import com.sora.domain.Keyword;
import com.sora.domain.Source;
import com.sora.service.IBlogService;
import com.sora.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/3 22:06
 */
@Service
public class BlogServiceImpl implements IBlogService {

    @Autowired
    private IBlogDao blogDao;

    public int addBlog(Blog blog)throws Exception{
        blogDao.addBlog(blog);
        if(blog.getSource()!=null){
            blogDao.addSource(blog);
        }
        return 1;
    }

    public int addBlog(Blog blog, MultipartFile source) throws Exception{
        blogDao.addBlog(blog);
        List<Keyword> keywordList = blog.getKeywordList();
        for(int i=0;i<keywordList.size();i++){
            Keyword keyword = keywordList.get(i);
            blogDao.addKeyword(keyword,blog);
        }
        if(source!=null){
            String location = blog.getId()+"_"+blog.getUser().getId()+".mp4";
            if(FileUtils.upload(source,location)){
                Source sourceTemp = new Source();
                sourceTemp.setLocation(location);
                blog.setSource(sourceTemp);
                blogDao.addSource(blog);
            }
        }
        return 1;
    }

    public List<Blog> findMyRelativedBlogs(long id) throws Exception{
        List<Blog> myRelativedBlogs = blogDao.findAllRelativedTeacherBlogByUserId(id);
        myRelativedBlogs.addAll(blogDao.findAllRelativedStudentBlogByUserId(id));
        myRelativedBlogs.sort(new Comparator<Blog>() {
            public int compare(Blog o1, Blog o2) {
                return (int)(o2.getId()-o1.getId());
            }
        });
        return myRelativedBlogs;
    }

    public List<Blog> findMyRelativedBlogsByKey(long id,String key) throws Exception{
        key = "%"+key+"%";
        List<Blog> myRelativedBlogs = blogDao.findAllRelativedTeacherBlogByUserIdAndKey(id,key);
        myRelativedBlogs.addAll(blogDao.findAllRelativedStudentBlogByUserIdAndKey(id,key));
        myRelativedBlogs.sort(new Comparator<Blog>() {
            public int compare(Blog o1, Blog o2) {
                return (int)(o2.getId()-o1.getId());
            }
        });
        return myRelativedBlogs;
    }

    public List<Blog> findRelativedBlogsByUserIdAndCourseId(long userId,long courseId) throws Exception{
        List<Blog> relativeBlogs = blogDao.findRelativedStudentBlogsByUserIdAndCourseId(userId,courseId);
        if(relativeBlogs.size()==0){
            relativeBlogs = blogDao.findRelativedTeacherBlogsByUserIdAndCourseId(userId,courseId);
        }
        return relativeBlogs;
    }

    public List<Blog> findStudentsBlogsByCourseId(long courseId) throws Exception{
        return blogDao.findStudentsBlogsByCourseId(courseId);
    }

    public List<Blog> findBlogsByUserId(long userId) throws Exception{
        List<Blog> relativeBlogs = blogDao.findStudentBlogsByUserId(userId);
        if(relativeBlogs.size()==0){
            relativeBlogs = blogDao.findTeacherBlogsByUserId(userId);
        }
        return relativeBlogs;
    }

    public int deleteBlog(Blog blog) throws Exception{
        return blogDao.deleteBlog(blog);
    }
}

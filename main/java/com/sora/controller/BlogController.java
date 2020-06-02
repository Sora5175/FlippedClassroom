package com.sora.controller;

import com.alibaba.fastjson.JSONObject;
import com.sora.domain.Blog;
import com.sora.domain.User;
import com.sora.service.IBlogService;
import com.sora.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/3 22:05
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @RequestMapping("/addBlog")
    @ResponseBody
    public ResultVO addBlog(@RequestAttribute long id, String blogJson, MultipartFile source){
        ResultVO resultVO = new ResultVO();
        try {
            Blog blog = JSONObject.parseObject(blogJson, Blog.class);
            blog.setPublishTime(new Date());
            User user = new User();
            user.setId(id);
            blog.setUser(user);
            resultVO.setResult(blogService.addBlog(blog,source));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/findMyRelativedBlogs")
    @ResponseBody
    public ResultVO findMyRelativedBlogs(@RequestAttribute long id){
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setResult(blogService.findMyRelativedBlogs(id));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/findMyRelativedBlogsByKey")
    @ResponseBody
    public ResultVO findMyRelativedBlogsByKey(@RequestAttribute long id,String key){
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setResult(blogService.findMyRelativedBlogsByKey(id,key));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/findMyRelativedBlogsByCourseId")
    @ResponseBody
    public ResultVO findMyRelativedBlogsByCourseId(@RequestAttribute long id,long courseId){
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setResult(blogService.findRelativedBlogsByUserIdAndCourseId(id,courseId));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/findBlogsByUserIdAndCourseId")
    @ResponseBody
    public ResultVO findBlogsByUserIdAndCourseId(long userId,long courseId){
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setResult(blogService.findRelativedBlogsByUserIdAndCourseId(userId,courseId));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/findStudentsBlogsByCourseId")
    @ResponseBody
    public ResultVO findStudentsBlogsByCourseId(long courseId){
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setResult(blogService.findStudentsBlogsByCourseId(courseId));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/findMyBlogs")
    @ResponseBody
    public ResultVO findMyBlogs(@RequestAttribute long id){
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setResult(blogService.findBlogsByUserId(id));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/deleteBlog")
    @ResponseBody
    public ResultVO deleteBlog(String blogJson){
        ResultVO resultVO = new ResultVO();
        try {
            Blog blog = JSONObject.parseObject(blogJson, Blog.class);
            resultVO.setResult(blogService.deleteBlog(blog));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/forwardBlog")
    @ResponseBody
    public ResultVO forwardBlog(@RequestAttribute long id, String blogJson){
        ResultVO resultVO = new ResultVO();
        try {
            Blog blog = JSONObject.parseObject(blogJson, Blog.class);
            User user = new User();
            user.setId(id);
            blog.setUser(user);
            resultVO.setResult(blogService.addBlog(blog));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

}

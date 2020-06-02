package com.sora.controller;

import com.alibaba.fastjson.JSONObject;
import com.sora.domain.Course;
import com.sora.domain.Question;
import com.sora.service.ICourseService;
import com.sora.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/2 21:11
 */
@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private ICourseService courseService;

    @RequestMapping("/addCourse")
    @ResponseBody
    public ResultVO addCourse(@RequestAttribute long id,String name){
        ResultVO resultVO = new ResultVO();
        try{
            resultVO.setResult(courseService.addCourse(id,name));
        }catch (Exception e){
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/findMyCourses")
    @ResponseBody
    public ResultVO findMyCourse(@RequestAttribute long id){
        ResultVO resultVO = new ResultVO();
        try{
            resultVO.setResult(courseService.findAllByUserId(id));
        }catch (Exception e){
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/joinCourse")
    @ResponseBody
    public ResultVO joinCourse(@RequestAttribute long id,long courseId){
        ResultVO resultVO = new ResultVO();
        try{
            resultVO.setResult(courseService.joinCourse(id,courseId));
        }catch (Exception e){
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/deleteCourse")
    @ResponseBody
    public ResultVO deleteCourse(@RequestAttribute long id,String courseJson){
        ResultVO resultVO = new ResultVO();
        try{
            Course course = JSONObject.parseObject(courseJson, Course.class);
            resultVO.setResult(courseService.deleteCourse(id,course));
        }catch (Exception e){
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }
}

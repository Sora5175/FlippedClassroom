package com.sora.controller;

import com.alibaba.fastjson.JSONObject;
import com.sora.domain.Blog;
import com.sora.domain.Question;
import com.sora.domain.User;
import com.sora.service.IQuestionService;
import com.sora.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/8 15:50
 */
@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    @RequestMapping("/findAllByBlogId")
    @ResponseBody
    public ResultVO findAllByBlogId(long blogId){
        ResultVO resultVO = new ResultVO();
        try{
            resultVO.setResult(questionService.findAllByBlogId(blogId));
        }catch (Exception e){
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/findAllByCourseId")
    @ResponseBody
    public ResultVO findAllByCourseId(long courseId){
        ResultVO resultVO = new ResultVO();
        try{
            resultVO.setResult(questionService.findAllByCourseId(courseId));
        }catch (Exception e){
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("findAllByCourseIdAndKey")
    @ResponseBody
    public ResultVO findAllByCourseIdAndKey(long courseId,String key){
        ResultVO resultVO = new ResultVO();
        try{
            resultVO.setResult(questionService.findAllByCourseIdAndKey(courseId,key));
        }catch (Exception e){
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/deleteQuestion")
    @ResponseBody
    public ResultVO deleteQuestion(@RequestAttribute long id, String questionJson){
        ResultVO resultVO = new ResultVO();
        try {
            Question question = JSONObject.parseObject(questionJson, Question.class);
            resultVO.setResult(questionService.deleteQuestion(id,question));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/importQuestions")
    @ResponseBody
    public ResultVO importQuestions(MultipartFile questions , long courseId){
        ResultVO resultVO = new ResultVO();
        try{
            resultVO.setResult(questionService.importQuestions(courseId,questions));
        }catch (Exception e){
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }
}

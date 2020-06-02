package com.sora.controller;

import com.alibaba.fastjson.JSONObject;
import com.sora.domain.Course;
import com.sora.domain.Keyword;
import com.sora.service.IKeywordService;
import com.sora.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Key;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/15 19:55
 */
@Controller
@RequestMapping("/keyword")
public class KeywordController {

    @Autowired
    private IKeywordService keywordService;

    @RequestMapping("/addKeyword")
    @ResponseBody
    public ResultVO addKeyword(@RequestAttribute long id, String name, long courseId){
        ResultVO resultVO = new ResultVO();
        try{
            resultVO.setResult(keywordService.addKeyword(name,courseId));
        }catch (Exception e){
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/deleteKeyword")
    @ResponseBody
    public ResultVO deleteKeyword(@RequestAttribute long id, String keywordJson){
        ResultVO resultVO = new ResultVO();
        try{
            Keyword keyword = JSONObject.parseObject(keywordJson,Keyword.class);
            resultVO.setResult(keywordService.deleteKeyword(keyword));
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
            resultVO.setResult(keywordService.findAllByCourseId(courseId));
        }catch (Exception e){
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }
}

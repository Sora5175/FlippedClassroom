package com.sora.controller;

import com.alibaba.fastjson.JSONObject;
import com.sora.domain.Comment;
import com.sora.domain.User;
import com.sora.service.ICommentService;
import com.sora.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/10 13:52
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @RequestMapping("/addComment")
    @ResponseBody
    public ResultVO addComment(@RequestAttribute long id,String commentJson,long blogId){
        ResultVO resultVO = new ResultVO();
        try {
            Comment comment = JSONObject.parseObject(commentJson, Comment.class);
            User user = new User();
            user.setId(id);
            comment.setUser(user);
            resultVO.setResult(commentService.addComment(comment,blogId));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/findAllByBlogId")
    @ResponseBody
    public ResultVO findAllByBlogId(long blogId){
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setResult(commentService.findAllByBlogId(blogId));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/topComment")
    @ResponseBody
    public ResultVO topComment(String commentJson){
        ResultVO resultVO = new ResultVO();
        try {
            Comment comment = JSONObject.parseObject(commentJson, Comment.class);
            resultVO.setResult(commentService.topComment(comment));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/deleteComment")
    @ResponseBody
    public ResultVO deleteComment(String commentJson){
        ResultVO resultVO = new ResultVO();
        try {
            Comment comment = JSONObject.parseObject(commentJson, Comment.class);
            resultVO.setResult(commentService.deleteComment(comment));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/findAllReplyComments")
    @ResponseBody
    public ResultVO findAllReplyComments(@RequestAttribute long id){
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setResult(commentService.findAllReplyByUserId(id));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }
}

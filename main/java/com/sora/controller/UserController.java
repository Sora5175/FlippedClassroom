package com.sora.controller;

import com.alibaba.fastjson.JSONObject;
import com.sora.domain.Student;
import com.sora.domain.User;
import com.sora.service.IUserService;
import com.sora.vo.ResultVO;
import com.sora.vo.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/30 21:24
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;


    @RequestMapping("/login")
    @ResponseBody
    public ResultVO login(HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        try {
            String token = request.getHeader("token");
            String code = request.getParameter("code");
            String avatarUrl = request.getParameter("avatarUrl");
            TokenVO tokenVO = new TokenVO();
            tokenVO = userService.login(token,code,avatarUrl);
            if(tokenVO.getUser() == null){
                resultVO.setStatus(202);
                resultVO.setMessage("不存在用户绑定此微信，请注册！");
            }else{
                resultVO.setResult(tokenVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/registerStudent")
    @ResponseBody
    public ResultVO registerStudent(String studentJson, String code,String avatarUrl){
        ResultVO resultVO = new ResultVO();
        try {
            Student student = JSONObject.parseObject(studentJson, Student.class);
            TokenVO tokenVO = new TokenVO();
            tokenVO = userService.registerStudent(student,code,avatarUrl);
            resultVO.setResult(tokenVO);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/getMyInfo")
    @ResponseBody
    public ResultVO getMyInfo(@RequestAttribute long id){
        ResultVO resultVO = new ResultVO();
        try{
            User user = userService.findInfoById(id);
            resultVO.setResult(user);
        }catch (Exception e){
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }
}

package com.sora.controller;

import com.sora.service.ISchoolService;
import com.sora.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/31 10:10
 */
@Controller
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private ISchoolService schoolService;

    @RequestMapping("/findAll")
    @ResponseBody
    public ResultVO findAll(){
        ResultVO resultVO = new ResultVO();
        resultVO.setResult(schoolService.findAll());
        return resultVO;
    }
}

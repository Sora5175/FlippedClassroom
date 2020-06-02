package com.sora.controller;

import com.alibaba.fastjson.JSONObject;
import com.sora.domain.Apply;
import com.sora.service.IApplyService;
import com.sora.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/16 20:45
 */
@Controller
@RequestMapping("/apply")
public class ApplyController {
    @Autowired
    private IApplyService applyService;


    @RequestMapping("/addApply")
    @ResponseBody
    public ResultVO addApply(@RequestAttribute long id){
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setResult(applyService.addApply(id));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/deleteApply")
    @ResponseBody
    public ResultVO deleteApply(String applyJson){
        ResultVO resultVO = new ResultVO();
        try {
            Apply apply = JSONObject.parseObject(applyJson,Apply.class);
            resultVO.setResult(applyService.deleteApply(apply));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/upgrade")
    @ResponseBody
    public ResultVO upgrade(String applyJson){
        ResultVO resultVO = new ResultVO();
        try {
            Apply apply = JSONObject.parseObject(applyJson,Apply.class);
            resultVO.setResult(applyService.upgrade(apply));
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public ResultVO findAll(){
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setResult(applyService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setStatus(500);
            resultVO.setMessage(e.getMessage());
        }
        return resultVO;
    }
}

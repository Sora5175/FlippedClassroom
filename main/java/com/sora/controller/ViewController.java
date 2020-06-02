package com.sora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/14 11:15
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/management")
    public String management(){
        return "management";
    }

    @RequestMapping("/adminIndex")
    public String adminIndex(){
        return "adminIndex";
    }
}

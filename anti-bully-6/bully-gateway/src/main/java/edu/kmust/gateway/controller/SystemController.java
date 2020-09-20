package edu.kmust.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/15 16:29
 **/
@Controller
public class SystemController {
    @GetMapping ("/login.html")
    public String login(){
        return "login";
    }

    @GetMapping ("/register.html")
    public ModelAndView register(){
        return new ModelAndView("register");
    }

    @GetMapping ("/forget.html")
    public ModelAndView forget(){
        return new ModelAndView("forget");
    }

    @GetMapping ("/fileupload.html")
    public ModelAndView fileupload(){
        return new ModelAndView("fileupload");
    }
}

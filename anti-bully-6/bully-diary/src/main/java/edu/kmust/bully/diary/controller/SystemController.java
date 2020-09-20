package edu.kmust.bully.diary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/16 4:01
 **/
@Controller
public class SystemController {
    @GetMapping("/Test")
    public ModelAndView upload(){
        return new ModelAndView("fileupload");
    }
}

package com.i2kiselev.springCourseProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String redirect(){
        return "redirect:/home ";
    }

    @GetMapping("")
    public String redirect2(){
        return "redirect:/home ";
    }
}

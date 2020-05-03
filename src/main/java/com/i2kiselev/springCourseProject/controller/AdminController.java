package com.i2kiselev.springCourseProject.controller;

import com.i2kiselev.springCourseProject.model.Roll;
import com.i2kiselev.springCourseProject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/console")
@Controller
public class AdminController {

    private final ProductService productService;

    public AdminController (ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/orders")
    public String console(Model model){
        model.addAttribute("roll", new Roll());
        return "orders";
    }

    @GetMapping("/addProduct")
    public String addProduct(){
        return "add-product";
    }

}

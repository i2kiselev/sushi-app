package com.i2kiselev.springCourseProject.controller;

import com.i2kiselev.springCourseProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/home")
@Controller
public class ClientController {

    private final ProductService productService;

    public ClientController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("currentUser", currentPrincipalName);
        model.addAttribute("products", productService.findAll());
        return "home";
    }

}

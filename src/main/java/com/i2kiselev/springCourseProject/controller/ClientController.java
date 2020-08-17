package com.i2kiselev.springCourseProject.controller;

import com.i2kiselev.springCourseProject.model.*;
import com.i2kiselev.springCourseProject.service.OrderService;
import com.i2kiselev.springCourseProject.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//
@RequestMapping("/home")
@Controller
public class ClientController {

    private final ProductService productService;
    private final OrderService orderService;

    public ClientController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @ModelAttribute("currentUser")
    public String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    @GetMapping("/")
    public String redirToHome2(){
        return "redirect:/";
    }
    @GetMapping("/rollsets")
    public String redirToHome(){
        return "redirect:/";
    }

    @GetMapping("")
    public String getRollsets(Model model) {
        model.addAttribute("products", productService.findAllRollSetsByStaff());
        return "rollsets";
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "products";
    }

    @GetMapping("/rolls")
    public String getRolls(Model model) {
        model.addAttribute("products", productService.findAllRolls());
        return "rolls";
    }

    @GetMapping("/product")
    public String getProduct(@RequestParam("id") Product product, Model model){
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/roll")
    public String getRolls(@RequestParam("id") Roll roll, Model model){
        model.addAttribute("roll", roll);
        return "roll";
    }

    @GetMapping("/rollset")
    public String getRollsets(@RequestParam("id") RollSet rollSet, Model model){
        model.addAttribute("rollset", rollSet);
        return "rollset";
    }

    @GetMapping("/orders")
    public String getOrders(@RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size, Model model){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Order> orderPage = orderService.getOrdersForCurrentUser(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("orders", orderPage);
        int totalPages = orderPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "client-orders";
    }

    @GetMapping("/addRollset")
    public String saveRollset(Model model){
        model.addAttribute("rollsetForm", new RollSetForm());
        model.addAttribute("rolls", productService.findAllRolls());
        return "add-rollset-client";
    }

    @PostMapping("/addRollset")
    public String saveRollSet(@ModelAttribute("rollset") RollSetForm rollSetForm, Model model) {
        RollSet rollset = productService.getRollSetFromInputForm(rollSetForm);
        rollset.setPrice(rollset.getFinalCost());
        productService.saveRollSet(rollset);
        model.addAttribute("rollsetForm", new RollSetForm());
        model.addAttribute("rolls", productService.findAllRolls());
        return "add-rollset-client";
    }

    @GetMapping("/clientRollsets")
    public String getClientRollsets(Model model){
        model.addAttribute("products", productService.findAllRollSetsByCurrentUser());
        return  "client-rollsets";
    }
}

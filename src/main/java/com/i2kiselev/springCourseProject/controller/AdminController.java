package com.i2kiselev.springCourseProject.controller;

import com.i2kiselev.springCourseProject.model.*;
import com.i2kiselev.springCourseProject.service.OrderService;
import com.i2kiselev.springCourseProject.service.ProductService;
import com.i2kiselev.springCourseProject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//Controller for staff dashboard
@Slf4j
@RequestMapping("/console")
@Controller
public class AdminController {

    private final ProductService productService;
    private final OrderService orderService;

    public AdminController(ProductService productService, OrderService orderService, UserService userService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/nextStatus")
    public String nextStatus(@RequestParam("id") Long orderId){
        orderService.nextStatus(orderId);
        return "redirect:/console/orders";
    }
    @GetMapping("/orders")
    public String getOrders(@RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size, Model model){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Order> orderPage = orderService.getUnfinishedOrders(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("orders", orderPage);
        int totalPages = orderPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        log.info("Returned staff's orders page to client");
        return "service-orders";
    }

    @GetMapping("/")
    public String redirectHome(){
        return "redirect:/console/orders";
    }

    @GetMapping("")
    public String redirectHome2(){
        return "redirect:/console/orders";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        log.info("Returned staff's new product page to client");
        return "add-product";
    }

    @PostMapping("/addProduct")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveImage(product);
        productService.saveProduct(product);
        return "add-product";
    }

    @GetMapping("/addRoll")
    public String addRoll(Model model){
        model.addAttribute("roll", new Roll());
        log.info("Returned staff's new roll page to client");
        return "add-roll";
    }

    @PostMapping("/addRoll")
    public String saveProduct(@ModelAttribute("roll") Roll roll) {
        productService.saveImage(roll);
        productService.saveRoll(roll);
        log.info("Returned staff's new product page to client");
        return "add-roll";
    }

    @GetMapping("/addRollset")
    public String saveRollset(Model model){
        model.addAttribute("rollsetForm", new RollSetForm());
        model.addAttribute("rolls", productService.findAllRolls());
        log.info("Returned staff's new rollset page to client");
        return "add-rollset";
    }

    @PostMapping("/addRollset")
    public String saveRollSet(@ModelAttribute("rollset") RollSetForm rollSetForm, Model model) {
        RollSet rollset = productService.getRollSetFromInputForm(rollSetForm);
        productService.saveImage(rollset);
        productService.saveRollSet(rollset);
        model.addAttribute("rollsetForm", new RollSetForm());
        model.addAttribute("rolls", productService.findAllRolls());
        return "add-rollset";
    }

    @GetMapping("/order")
    public String getOrder(@RequestParam("id") Order order, Model model){
        model.addAttribute("order", order);
        return "order";
    }
}
